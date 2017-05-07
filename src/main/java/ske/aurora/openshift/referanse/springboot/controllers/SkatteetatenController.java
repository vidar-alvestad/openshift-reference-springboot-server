package ske.aurora.openshift.referanse.springboot.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class SkatteetatenController {

    private static final Logger logger = LoggerFactory.getLogger(SkatteetatenController.class);

    private RestTemplate restTemplate;

    public SkatteetatenController(RestTemplate restTemplate) {

        this.restTemplate = restTemplate;
    }

    @GetMapping("/api/skatteetaten")
    public String skatteetaten() {

        ResponseEntity<String> forEntity = restTemplate.getForEntity("http://skatteetaten.no", String.class);
        logger.info("{}", forEntity.getStatusCode());
        return forEntity.getBody();
    }

}

