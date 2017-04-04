package ske.aurora.openshift.referanse.springboot;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import ske.aurora.openshift.referanse.springboot.config.ApplicationConfig;

@SpringBootApplication
public class Main {

    private static final Logger LOG = LoggerFactory.getLogger(ApplicationConfig.class);

    protected Main() {
    }

    public static void main(String[] args) throws Exception {
        LOG.debug("Setter opp database med navn REFERANSEAPP");
        setDatasourcePropertiesFromDbPropertiesFile("REFERANSEAPP_DB_PROPERTIES");

        SpringApplication.run(Main.class, args);
    }

    /**
     * Denne metoden leser JDBC tilkoblingsinformasjon fra en properties fil i formatet fra Sprocket og setter
     * tilsvarende spring relaterte datasource properties.
     *
     * @param propertiesFile
     * @throws IOException
     */
    static void setDatasourcePropertiesFromDbPropertiesFile(String propertiesFile) throws IOException {

        String databasePath = System.getenv(propertiesFile);

        if (databasePath == null) {
            return;
        }
        Properties props = new Properties();

        try (FileInputStream input = new FileInputStream(databasePath)) {
            props.load(input);
        }

        System.setProperty("spring.datasource.url", props.getProperty("jdbc.url"));
        System.setProperty("spring.datasource.username", props.getProperty("jdbc.user"));
        System.setProperty("spring.datasource.password", props.getProperty("jdbc.password"));
    }
}
