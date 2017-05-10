package ske.aurora.openshift.referanse.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import ske.aurora.annotations.AuroraApplication;

@SpringBootApplication
@AuroraApplication
public class Main {

    protected Main() {
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Main.class, args);
    }

}
