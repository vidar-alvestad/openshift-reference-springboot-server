package no.skatteetaten.aurora.openshift.reference.springboot.controllers;

import static no.skatteetaten.aurora.prometheus.collector.Operation.withMetrics;
import static no.skatteetaten.aurora.prometheus.collector.Size.size;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import no.skatteetaten.aurora.openshift.reference.springboot.service.CounterDatabaseService;

/**
 * This is an example Controller that demonstrates a very simple controller that increments a counter
 * in a oracle sql database and returns the previous value.
 * <p>
 * There should automatically be registered metrics for both the withMetrics block and the http endpoint
 * execute and http_server_request histograms
 */

@RestController
public class CounterController {

    private CounterDatabaseService service;

    public CounterController(CounterDatabaseService service) {
        this.service = service;
    }

    @GetMapping("/api/counter")
    public String counter() {

        String value = withMetrics("counter", "DATABASE_READ", () ->
            service.getAndIncrementCounter().get("value").toString()
        );

        size("counter", "database", Integer.parseInt(value));
        return value;
    }

}
