package no.skatteetaten.aurora.openshift.reference.springboot.health

import org.springframework.boot.actuate.health.Health

import no.skatteetaten.aurora.openshift.reference.springboot.service.CounterDatabaseService
import spock.lang.Specification

class CounterHealthTest extends Specification {

  def counterDatabaseService = Mock(CounterDatabaseService)
  def counterHealth = new CounterHealth(counterDatabaseService)
  def builder = new Health.Builder()

  def "Verifies status varies on counter value"() {

    when:
      counterDatabaseService.getCounter() >> counter
      counterHealth.doHealthCheck(builder)

    then:
      builder.build().status.code == status

    where:
      counter | status
      2       | "OBSERVE"
      3       | "UP"
  }
}
