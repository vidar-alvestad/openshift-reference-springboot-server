package ske.aurora.openshift.referanse.springboot

import spock.lang.Specification

class MainTest extends Specification {

  static final ENV_NAME = "APP_DB_PROPERTIES"

  def databaseProperties = ["spring.datasource.url", "spring.datasource.username", "spring.datasource.password"]

  def "Database properties are not set when env not set"() {

    when:
      Main.setDatasourcePropertiesFromDbPropertiesFile(ENV_NAME)

    then:
      !databaseProperties.any { System.getProperty(it) }
  }

  def "Database properties are set when env set"() {

    given:
      EnvironmentUtils.setEnv([(ENV_NAME): new File("./src/test/resources/os-db.properties").absoluteFile.absolutePath])

    when:
      Main.setDatasourcePropertiesFromDbPropertiesFile(ENV_NAME)

    then:
      databaseProperties.every { System.getProperty(it) }
  }

  def "Sonar made me do it"() {
    expect:
      new Main()
  }
}
