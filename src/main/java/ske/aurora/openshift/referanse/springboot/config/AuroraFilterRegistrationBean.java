package ske.aurora.openshift.referanse.springboot.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ryantenney.metrics.spring.config.annotation.EnableMetrics;
import com.ryantenney.metrics.spring.config.annotation.MetricsConfigurerAdapter;

import ske.aurora.filter.logging.AuroraHeaderFilter;

@Configuration
@EnableMetrics
public class ApplicationConfig extends MetricsConfigurerAdapter {

    @Bean
    public FilterRegistrationBean auroraHeaderFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.addUrlPatterns("/api/*");
        registration.setFilter(new AuroraHeaderFilter());
        return registration;
    }
}
