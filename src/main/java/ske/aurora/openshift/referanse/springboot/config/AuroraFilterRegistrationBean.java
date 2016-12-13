package ske.aurora.openshift.referanse.springboot.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ske.aurora.filter.logging.AuroraHeaderFilter;

@Configuration
public class AuroraFilterRegistrationBean {

    @Bean
    public FilterRegistrationBean auroraHeaderFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.addUrlPatterns("/api/*");
        registration.setFilter(new AuroraHeaderFilter());
        return registration;
    }
}
