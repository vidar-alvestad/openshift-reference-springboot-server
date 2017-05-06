package ske.aurora.openshift.referanse.springboot.controllers;

import static ske.aurora.prometheus.Execute.withMetrics;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import ske.aurora.openshift.referanse.springboot.service.CounterDatabaseService;

/**
 * This is an example Controller that demonstrates a very simple JSON-over-HTTP enpoint. An example of how to use
 * metrics to register the execution times is also included.
 */

@RestController
public class CounterController {

    private static final Logger logger = LoggerFactory.getLogger(CounterController.class);
    public static final int SECOND = 1000;
    private RestTemplate client;
    private CounterDatabaseService service;

    public CounterController(RestTemplate client, CounterDatabaseService service) {

        this.client = client;
        this.service = service;
    }

    @GetMapping("/api/counter")
    public String counter() {
        ResponseEntity<String> forEntity = client.getForEntity("http://localhost:8080/foo", String.class);
        logger.info("{}", forEntity.getStatusCode());
        return forEntity.getBody();
    }

    @GetMapping("/foo")
    public String counter2() {
        return withMetrics(this.getClass(), "test1", () -> {
            long sleepTime = (long) (Math.random() * SECOND);

            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                throw new RuntimeException("Sleep interupted", e);
            }

            if (sleepTime % 2 == 0) {
                return service.getAndIncrementCounter().get("value").toString();
            } else {
                throw new RuntimeException("This is a test");
            }
        });
    }

}
