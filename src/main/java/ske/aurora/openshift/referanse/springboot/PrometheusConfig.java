package ske.aurora.openshift.referanse.springboot;

import java.util.Collection;

import javax.annotation.PostConstruct;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.PublicMetrics;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.logback.InstrumentedAppender;

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

    @Bean
    public SpringBootMetricsCollector metricsCollector(Collection<PublicMetrics> metrics, CollectorRegistry registry) {

        return new SpringBootMetricsCollector(metrics).register(registry);
    }

    @Bean
    public ServletRegistrationBean exporterServlet(final CollectorRegistry registry) {

        return new ServletRegistrationBean(new MetricsServlet(registry), "/prometheus");
    }

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
}