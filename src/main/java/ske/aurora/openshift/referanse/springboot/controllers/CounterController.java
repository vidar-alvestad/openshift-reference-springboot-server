package ske.aurora.openshift.referanse.springboot.controllers;

import static ske.aurora.prometheus.collector.Execute.withMetrics;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import ske.aurora.openshift.referanse.springboot.Main;
import ske.aurora.openshift.referanse.springboot.service.CounterDatabaseService;

/**
 * This is an example Controller that demonstrates a very simple controller that increments a counter
 * in a oracle sql database and returns the previous value.
 *
 * There should automatically be registered metrics for both the withMetrics block and the http endpoint
 * execute and http_server_request histograms
 */

@RestController
public class CounterController {

    public static final int SECOND = 1000;
    private CounterDatabaseService service;

    public CounterController(CounterDatabaseService service){
        this.service = service;
    }

    @GetMapping("/api/counter")
    public String counter() {
        return withMetrics(this.getClass(), "test1", () -> {
            long sleepTime = (long) (Math.random() * SECOND);

            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
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
