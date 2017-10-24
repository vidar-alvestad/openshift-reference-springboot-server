package no.skatteetaten.aurora.openshift.reference.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import io.micrometer.spring.autoconfigure.export.StringToDurationConverter;

@SpringBootApplication
//TODO: The following two lines can be removed once next rc of micrometer is out.
@Configuration
@Import(StringToDurationConverter.class)
public class Main {

    protected Main() {
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Main.class, args);
    }
}
