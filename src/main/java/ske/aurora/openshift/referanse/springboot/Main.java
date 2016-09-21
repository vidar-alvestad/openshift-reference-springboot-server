package ske.aurora.openshift.referanse.springboot;

import java.io.FileReader;
import java.util.Properties;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ske.aurora.logging.korrelasjon.RequestKorrelasjon;

@SpringBootApplication
@RestController
public class Main {

    public static void main(String[] args) throws Exception {

        String databasePath = System.getenv("REFERANSEAPP_DB_PROPERTIES");

        if (databasePath != null) {
            Properties props = new Properties();
            try (FileReader reader = new FileReader(databasePath)) {
                props.load(reader);
            }

            System.setProperty("spring.datasource.url", props.getProperty("jdbc.url"));
            System.setProperty("spring.datasource.username", props.getProperty("jdbc.user"));
            System.setProperty("spring.datasource.password", props.getProperty("jdbc.password"));
        }

        SpringApplication.run(Main.class, args);
    }

    @RequestMapping("/test")
    public String test() {
        return "test";
    }

    @RequestMapping("/api/korrelasjonsid")
    public String getKorrelasjonsId() {
        return RequestKorrelasjon.getId();
    }
}
