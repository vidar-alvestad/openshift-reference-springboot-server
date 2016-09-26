package ske.aurora.openshift.referanse.springboot.health;

import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.stereotype.Component;

import ske.aurora.openshift.referanse.springboot.service.CounterService;

@Component
public class CounterHealth extends AbstractHealthIndicator {

    private final CounterService counterService;

    public CounterHealth(CounterService counterService) {
        this.counterService = counterService;
    }

    @Override
    protected void doHealthCheck(Health.Builder builder) throws Exception {
        int currentValue = Integer.parseInt(counterService.getAndIncrementCounter().get("VALUE").toString());

        if (currentValue % 4 == 1) {
            builder.outOfService();
        } else if (currentValue % 2 == 1) {
            builder.down();
        } else {
            builder.up();
        }
    }
}
