package ske.aurora.openshift.referanse.springboot.health;

import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.stereotype.Component;

import ske.aurora.openshift.referanse.springboot.service.CounterService;

/**
 * A sample custom health check. You can add your own health checks that verifies the proper operational status of your
 * application.
 */
@Component
public class CounterHealth extends AbstractHealthIndicator {

    private final CounterService counterService;

    public CounterHealth(CounterService counterService) {
        this.counterService = counterService;
    }

    @Override
    protected void doHealthCheck(Health.Builder builder) throws Exception {
        int currentValue = counterService.getCounter();

        if (currentValue % 2 == 0) {
            builder.status("COMMENT").withDetail("message", "Partall antall i teller")
                .withDetail("Antall", currentValue);
        } else {
            builder.up()
                .withDetail("Antall", currentValue);
        }
    }
}
