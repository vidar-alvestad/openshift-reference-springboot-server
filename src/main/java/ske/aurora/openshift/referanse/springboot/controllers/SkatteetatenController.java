package ske.aurora.openshift.referanse.springboot.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/*
 * An example controller that does a request to another resource
 * This call should automatically have the KorrelasjonsId and ClientId headers added
 * There should be a metric called http_client_request_bucket recorded with the response
 */
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

