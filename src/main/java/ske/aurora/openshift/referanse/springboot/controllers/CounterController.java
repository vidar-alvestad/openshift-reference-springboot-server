package ske.aurora.openshift.referanse.springboot.controllers;


import static ske.aurora.prometheus.Execute.withMetrics;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import ske.aurora.openshift.referanse.springboot.service.CounterService;

/**
 * This is an example Controller that demonstrates a very simple JSON-over-HTTP enpoint. An example of how to use
 * metrics to register the execution times is also included.
 */

@RestController
public class CounterController {

    private static final Logger logger = LoggerFactory.getLogger(CounterController.class);
    private RestTemplate client;
    private JdbcTemplate jdbcTemplate;
    private CounterService service;

    public CounterController(RestTemplate client, JdbcTemplate jdbcTemplate) {

        this.client = client;
        this.jdbcTemplate = jdbcTemplate;
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
            long sleepTime = (long) (Math.random() * 1000);

            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                throw new RuntimeException("Sleep interupted", e);
            }

            if (sleepTime % 2 == 0) {
                return getAndIncrementCounter().get("value").toString();
            } else {
                throw new RuntimeException("This is a test");
            }
        });
    }


    @Transactional
    public Map<String, Object> getAndIncrementCounter() {
        Map<String, Object> counter = jdbcTemplate.queryForMap("SELECT value FROM counter FOR UPDATE OF value");
        jdbcTemplate.update("UPDATE counter SET value=value+1");
        return counter;
    }

    public int getCounter() {
        Integer integer = jdbcTemplate.queryForObject("SELECT value FROM counter", Integer.class);
        if (integer == null) {
            return 0;
        }
        return integer;
    }

}
