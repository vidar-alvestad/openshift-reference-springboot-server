package ske.aurora.openshift.referanse.springboot;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ske.aurora.logging.korrelasjon.KorrelasjonsFilter;

@Configuration
public class ApplicationConfig {

    @Bean
    public FilterRegistrationBean korrelasjonsFilterRegistrationBean() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.addUrlPatterns("/api/*");
        registration.setFilter(new KorrelasjonsFilter());
        return registration;
    }
}
