package ske.aurora.openshift.referanse

import java.util.concurrent.atomic.AtomicInteger
import java.util.function.Supplier

import com.codahale.metrics.Metric
import com.codahale.metrics.MetricRegistry

import ske.aurora.openshift.referanse.test.Execute
import spock.lang.Shared
import spock.lang.Specification

class ExecuteTest extends Specification {
  @Shared
  MetricRegistry metricRegistry;
  AtomicInteger healthError;

  def setup() {
    metricRegistry = new MetricRegistry()
    healthError = new AtomicInteger(0);
  }

  def "Should not increase health if supplier succeeds"() {
    given:
      Supplier s = new Supplier() {
        @Override
        Object get() {
          return true;
        }
      }
    when:
      Execute.withHealth(healthError, s)
    then:
      healthError.get() == 0
  }

  def "Should increase health if supplier throws Exception"() {
    given:
      Supplier s = new Supplier() {
        @Override
        Object get() {
          throw new RuntimeException()
        }
      }
    when:
      Execute.withHealth(healthError, s)
    then:
      healthError.get() == 1
      thrown RuntimeException
  }

  def "Should reset health if supplier resumes normal operation"() {
    given:
      healthError.set(10)
      Supplier s = new Supplier() {
        @Override
        Object get() {
          return true;
        }
      }
    when:
      Execute.withHealth(healthError, s)
    then:
      healthError.get() == 0
  }

  def "Should set metrics with class"() {
    given:
      Supplier s = new Supplier() {
        @Override
        Object get() {
          return true;
        }
      }
    when:
      Execute.withMetrics(this.class, "test", metricRegistry, healthError, s)
    then:
      Map<String, Metric> metrics = metricRegistry.getMetrics()
      metrics.containsKey(this.class.getName() + ".test." + Execute.EXECUTION_TIMER_SUFFIX)
      metrics.containsKey(this.class.getName() + ".test." + Execute.SUCCESS_COUNTER_SUFFIX)
      metrics.containsKey(this.class.getName() + ".test." + Execute.ERROR_COUNTER_SUFFIX)
      healthError.get() == 0
  }

  def "Should set metrics with base name"() {
    given:
      Supplier s = new Supplier() {
        @Override
        Object get() {
          return true;
        }
      }
    when:
      Execute.withMetrics("test", metricRegistry, healthError, s)
    then:
      Map<String, Metric> metrics = metricRegistry.getMetrics()
      metrics.containsKey("test." + Execute.EXECUTION_TIMER_SUFFIX)
      metrics.containsKey("test." + Execute.SUCCESS_COUNTER_SUFFIX)
      metrics.containsKey("test." + Execute.ERROR_COUNTER_SUFFIX)
      healthError.get() == 0
  }

  def "Should set timer metric"() {
    given:
      Supplier s = new Supplier() {
        @Override
        Object get() {
          return true;
        }
      }
    when:
      Execute.withMetrics("test", metricRegistry, healthError, s)
    then:
      metricRegistry.timer("test." + Execute.EXECUTION_TIMER_SUFFIX).count == 1
      healthError.get() == 0
  }

  def "Should increase success metric if supplier succeeds"() {
    given:
      Supplier s = new Supplier() {
        @Override
        Object get() {
          return true;
        }
      }
    when:
      Execute.withMetrics("test", metricRegistry, healthError, s)
    then:
      metricRegistry.counter("test." + Execute.SUCCESS_COUNTER_SUFFIX).count == 1
      metricRegistry.counter("test." + Execute.ERROR_COUNTER_SUFFIX).count == 0
      healthError.get() == 0
  }

  def "Should increase error metric if supplier throws Exception"() {
    given:
      Supplier s = new Supplier() {
        @Override
        Object get() {
          throw new RuntimeException()
        }
      }
    when:
      Execute.withMetrics("test", metricRegistry, healthError, s)
    then:
      metricRegistry.counter("test." + Execute.SUCCESS_COUNTER_SUFFIX).count == 0
      metricRegistry.counter("test." + Execute.ERROR_COUNTER_SUFFIX).count == 1
      healthError.get() == 1
      thrown RuntimeException
  }

  def "Should reset health if supplier resumes normal operation, with metrics"() {
    given:
      healthError.set(10)
      Supplier s = new Supplier() {
        @Override
        Object get() {
          return true;
        }
      }
    when:
      Execute.withMetrics("test", metricRegistry, healthError, s)
    then:
      metricRegistry.counter("test." + Execute.SUCCESS_COUNTER_SUFFIX).count == 1
      healthError.get() == 0
  }

}

