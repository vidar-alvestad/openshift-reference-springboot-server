package ske.aurora.openshift.referanse.springboot;

import java.util.Collection;

import org.springframework.boot.actuate.endpoint.PublicMetrics;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.exporter.MetricsServlet;
import io.prometheus.client.spring.boot.SpringBootMetricsCollector;

@Configuration
public class PrometheusConfig {

    @Bean
    public CollectorRegistry collectorRegistry() {
        return new CollectorRegistry();
    }

    @Bean
    public SpringBootMetricsCollector metricsCollector(Collection<PublicMetrics> metrics, CollectorRegistry registry) {

        return new SpringBootMetricsCollector(metrics).register(registry);
    }

    @Bean
    public ServletRegistrationBean exporterServlet(final CollectorRegistry registry) {

        return new ServletRegistrationBean(new MetricsServlet(registry), "/prometheus");
    }
}