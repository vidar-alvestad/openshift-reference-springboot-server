package ske.aurora.openshift.referanse.springboot;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@SuppressWarnings({ "checkstyle:hideutilityclassconstructor" })
public class Main {

    public static void main(String[] args) throws Exception {

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
    private static void setDatasourcePropertiesFromDbPropertiesFile(String propertiesFile) throws IOException {

        String databasePath = System.getenv(propertiesFile);

        if (databasePath == null) {
            return;
        }
        Properties props = new Properties();
        try (FileReader reader = new FileReader(databasePath)) {
            props.load(reader);
        }

        System.setProperty("spring.datasource.url", props.getProperty("jdbc.url"));
        System.setProperty("spring.datasource.username", props.getProperty("jdbc.user"));
        System.setProperty("spring.datasource.password", props.getProperty("jdbc.password"));
    }
}
