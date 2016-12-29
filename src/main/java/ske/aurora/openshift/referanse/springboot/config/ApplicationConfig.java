package ske.aurora.openshift.referanse.springboot.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertiesPropertySource;

import com.ryantenney.metrics.spring.config.annotation.EnableMetrics;
import com.ryantenney.metrics.spring.config.annotation.MetricsConfigurerAdapter;

import ske.aurora.filter.logging.AuroraHeaderFilter;

@Configuration
@EnableMetrics
public class ApplicationConfig extends MetricsConfigurerAdapter {

    @Autowired
    private ConfigurableEnvironment env;

    @Bean
    public FilterRegistrationBean auroraHeaderFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.addUrlPatterns("/api/*");
        registration.setFilter(new AuroraHeaderFilter());
        return registration;
    }

    @Bean
    public PropertiesPropertySource secretProperties() throws IOException {
        return createAuroraPropertySource("auroraConfig[secret]", "AURORA_SECRET_PREFIX");
    }

    @Bean
    public PropertiesPropertySource configProperties() throws IOException {
        return createAuroraPropertySource("auroraConfig[env]", "AURORA_ENV_PREFIX");
    }

    @Bean
    public PropertiesPropertySource auroraProperties() throws IOException {


        Properties props = new Properties();
        Stream.of("AURORA_VERSION", "APP_VERSION")
            .forEach(p ->props.put(p, System.getenv(p)));

        PropertiesPropertySource imageProps = new PropertiesPropertySource("auroraConfig[image]", props);

        env.getPropertySources().addLast(imageProps);
        return imageProps;
    }

    private PropertiesPropertySource createAuroraPropertySource(String name, String envPrefix) throws IOException {
        String secretPrefix = System.getenv(envPrefix);

        if (secretPrefix == null) {
            return null;
        }

        String propertiesName = secretPrefix + ".properties";
        Properties props = new Properties();
        props.load(new FileInputStream(propertiesName));

        PropertiesPropertySource secretProps = new PropertiesPropertySource(name, props);

        env.getPropertySources().addLast(secretProps);
        return secretProps;
    }
}
