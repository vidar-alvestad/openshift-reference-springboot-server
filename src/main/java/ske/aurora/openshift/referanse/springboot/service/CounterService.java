package ske.aurora.openshift.referanse.springboot.service;

import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * An example service that demonstrates basic database operations.
 */
@Service
public class CounterService {

    private final JdbcTemplate jdbcTemplate;

    public CounterService(JdbcTemplate jdbcTemplate) {

        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional
    public Map<String, Object> getAndIncrementCounter() {
        Map<String, Object> counter = jdbcTemplate.queryForMap("select value from counter for update of value");
        jdbcTemplate.update("update counter set value=value+1");
        return counter;
    }

    public int getCounter() {
        Integer integer = jdbcTemplate.queryForObject("select value from counter", Integer.class);
        if (integer == null) {
            return 0;
        }
        return integer;
    }
}
