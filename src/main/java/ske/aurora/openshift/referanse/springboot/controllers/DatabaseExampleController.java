package ske.aurora.openshift.referanse.springboot.controllers;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import ske.aurora.openshift.referanse.springboot.service.CounterService;

@RestController
public class DatabaseExampleController {

    private final CounterService counterService;

    public DatabaseExampleController(CounterService counterService) {

        this.counterService = counterService;
    }

    @GetMapping("/api/counter")
    public Map<String, Object> counter() {

        return counterService.getAndIncrementCounter();
    }
}
