package ske.aurora.openshift.referanse.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import ske.aurora.annotations.AuroraApplication;

@SpringBootApplication(scanBasePackages = "ske.aurora")
@AuroraApplication
public class Main {

    protected Main() {
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Main.class, args);
    }

}