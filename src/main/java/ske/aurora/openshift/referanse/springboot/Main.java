package ske.aurora.openshift.referanse.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @RequestMapping("/test")
    public String test() {
        return "test";
    }


    @RequestMapping("/api/test")
    public String apiTest() {
        return "apiTest";
    }
}
