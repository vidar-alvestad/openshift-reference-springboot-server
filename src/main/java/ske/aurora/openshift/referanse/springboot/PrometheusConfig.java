package ske.aurora.openshift.referanse.springboot;

import static java.util.Arrays.asList;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.PublicMetrics;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.logback.InstrumentedAppender;
import com.codahale.metrics.servlet.AbstractInstrumentedFilter;
import com.codahale.metrics.servlet.InstrumentedFilter;
import com.codahale.metrics.servlet.InstrumentedFilterContextListener;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.dropwizard.DropwizardExports;
import io.prometheus.client.exporter.MetricsServlet;
import io.prometheus.client.hotspot.DefaultExports;
import io.prometheus.client.spring.boot.SpringBootMetricsCollector;

@Configuration
public class PrometheusConfig {

    @Autowired
    private MetricRegistry metricRegistry;

    @Bean
    public CollectorRegistry collectorRegistry() {

        return CollectorRegistry.defaultRegistry;
    }

    /**
     * Register a metrics collector that will collect all spring boot metrics
     *
     * @param metrics
     * @param registry
     * @return
     */
    @Bean
    public SpringBootMetricsCollector metricsCollector(Collection<PublicMetrics> metrics, CollectorRegistry registry) {

        return new SpringBootMetricsCollector(metrics).register(registry);
    }

    /**
     * Register the prometheus exporter servlet
     *
     * @param registry
     * @return
     */
    @Bean
    public ServletRegistrationBean exporterServlet(final CollectorRegistry registry) {

        return new ServletRegistrationBean(new MetricsServlet(registry), "/prometheus");
    }

    /**
     * Create a FilterRegistrationBean that will register a filter for collecting request counting metrics for
     * any request starting with /api
     *
     * @return
     */
    @Bean
    public FilterRegistrationBean apiInstrumentedFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.addUrlPatterns("/api/*");
        registration.setFilter(new RequestStatusCodeInstrumentedFilter("responseCodes"));
        registration.addInitParameter("name-prefix", "api");
        return registration;
    }

    /**
     * This bean will make sure the MetricRegistry is put in the servlet context at a key
     * (InstrumentedFilter.REGISTRY_ATTRIBUTE) where the RequestStatusCodeInstrumentedFilter can find it.
     *
     * @return
     */
    @Bean
    public InstrumentedFilterContextListener metricsRegistryContextListener() {
        return new InstrumentedFilterContextListener() {

            @Override
            protected MetricRegistry getMetricRegistry() {
                return metricRegistry;
            }
        };
    }

    /**
     * Configure default metrics (JVM and logging)
     */
    @PostConstruct
    public void configureMetrics() {

        new DropwizardExports(metricRegistry).register();

        DefaultExports.initialize();

        LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
        InstrumentedAppender appender = new InstrumentedAppender(metricRegistry);
        appender.setContext(lc);
        appender.start();

        Logger logger = (Logger) LoggerFactory.getLogger("root");
        logger.addAppender(appender);
    }

    public static class RequestStatusCodeInstrumentedFilter extends AbstractInstrumentedFilter {

        private static final List<HttpStatus> STATUS_CODES =
            asList(OK, CREATED, NO_CONTENT, BAD_REQUEST, NOT_FOUND, INTERNAL_SERVER_ERROR);

        public RequestStatusCodeInstrumentedFilter(String metricNamePrefix) {

            super(InstrumentedFilter.REGISTRY_ATTRIBUTE, createMeterNamesByStatusCode(metricNamePrefix, STATUS_CODES),
                metricNamePrefix + ".other");
        }

        private static Map<Integer, String> createMeterNamesByStatusCode(String namePrefix,
            List<HttpStatus> statusCodes) {

            final Map<Integer, String> meterNamesByStatusCode = new HashMap<>();
            statusCodes.forEach(httpStatus -> {
                String metricName = String.format("%s.%s", namePrefix, httpStatus.name());
                meterNamesByStatusCode.put(httpStatus.value(), metricName);
            });
            return meterNamesByStatusCode;
        }
    }
}
