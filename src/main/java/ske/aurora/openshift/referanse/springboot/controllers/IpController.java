package ske.aurora.openshift.referanse.springboot.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;

/*
 * An example controller that does a request to another resource
 * This call should automatically have the KorrelasjonsId and ClientId headers added
 * There should be a metric called http_client_request_bucket recorded with the response
 */
@RestController
public class IpController {

    private RestTemplate restTemplate;

    public IpController(RestTemplate restTemplate) {

        this.restTemplate = restTemplate;
    }

    @GetMapping("/api/ip")
    public String ip() {
        JsonNode forEntity = restTemplate.getForObject("http://httpbin.org/ip", JsonNode.class);
        return forEntity.get("origin").textValue();
    }

}

