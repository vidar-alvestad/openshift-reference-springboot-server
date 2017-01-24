package ske.aurora.openshift.referanse.springboot.controllers;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;

import ske.aurora.openshift.referanse.springboot.service.CounterService;

/**
 * This is an example Controller that demonstrates a very simple JSON-over-HTTP enpoint. An example of how to use
 * metrics to register the execution times is also included.
 */
@RestController
public class CounterController {

    private static final Logger logger = LoggerFactory.getLogger(CounterController.class);

    private final CounterService counterService;

    public CounterController(CounterService counterService) {

        this.counterService = counterService;
    }

    @Timed(name = "counter")
    @GetMapping("/api/counter")
    public Map<String, Object> counter() {

        Map<String, Object> counter = counterService.getAndIncrementCounter();
        logger.debug("Incrementing counter to {}", counter.get("VALUE"));
        return counter;
    }
}
