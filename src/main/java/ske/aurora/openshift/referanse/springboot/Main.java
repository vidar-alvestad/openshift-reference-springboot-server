package ske.aurora.openshift.referanse.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
/*
@PropertySource("classpath:aurora-openshift-spring-boot-starter.properties")
@EnablePrometheusEndpoint
@EnableSpringBootMetricsCollector*/
public class Main {

    protected Main() {
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Main.class, args);
    }

}
