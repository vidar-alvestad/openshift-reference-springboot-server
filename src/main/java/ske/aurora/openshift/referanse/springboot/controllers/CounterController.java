package ske.aurora.openshift.referanse.springboot.controllers;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;

import ske.aurora.openshift.referanse.springboot.service.CounterService;

@RestController
public class CounterController {

    private static final Logger LOG = LoggerFactory.getLogger(CounterController.class);

    private final CounterService counterService;

    public CounterController(CounterService counterService) {

        this.counterService = counterService;
    }

    @Timed(name = "counter")
    @GetMapping("/api/counter")
    public Map<String, Object> counter() {

        Map<String, Object> counter = counterService.getAndIncrementCounter();
        LOG.debug("Incrementing counter to {}", counter.get("VALUE"));
        return counter;
    }
}
