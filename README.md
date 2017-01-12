# Spring Boot Reference Application

The intention of the Reference Application is to serve as a guide when developing "Fagapplikasjoner" within
Skatteetaten; applications implementing the core business rules in the tax domain for which Skatteetaten is responsible.

In this repository your will find examples on how to solve common technical issues and implement requirements for 
applications running within the networks of Skatteetaten and especially on the Aurora Openshift platform.
This includes logging, handling database migrations, testing, security, application versioning, build pipeline, to name 
a few.

The Reference Application is implemented in Spring Boot, which is currently the recommended technology for all new
applications.


# About the Core Technologies

The recommended technology for all new business applications created within Skatteetaten is Spring Boot. Recommending
a technology in this way is always associated with some controversy, especially in an organization with history of using
several other technology stacks. It is, however, our opinion that at the time there are few other technology stacks
that are better supported and are driving development in the Cloud Native space (a requirement when running on a PaaS
like Openshift) better than Spring Boot.

For more information on the decision process around selecting Spring Boot, see [reference missing].


# The Aurora Requirements



# What is Covered in the Application?

## Log Configuration

All Skatteetaten applications should log using the same standard logging pattern. Also, all applications running
on Openshift has to log to a specific folder in the container for the logs to picked up and automatically indexed in
Splunk. Logfiles put in the container need to be rolled before reaching a predetermined size to avoid the platform
terminating the application (in self defence; log files filling up disk on the Openshift nodes may wreck havoc in a 
cluster).

To avoid every application having to maintain their own ```logback.xml```-file implementing all the requirements, one
is provided by the platform in the runtime Docker image. The reference application is configured to use this Logback
file when it exists, while still preserving the convenient Spring Boot features for setting the log level. This is done
in the ```src/main/assembly/metadata/openshift.json```-file. The ```application.yml```-file also contains an example on
how to set the log levels.

For details, see:
* [OPENSHIFT: Hvordan få riktig konfigurert logging](https://aurora/wiki/pages/viewpage.action?pageId=121982307)
* [Logging og feilhåndtering - Krav](https://aurora/wiki/pages/viewpage.action?pageId=34578383)


## HTTP Header Handling

According to the Aurora Requirements all applications should use the ```Klientid``` header for identifying themeselves
when they operate as a client to other services. They should also send a ```Korrelasjonsid``` header for traceability.
They may also optionally send a ```Meldingsid``` header as an identifier for the current message.

The value of the ```Klientid```, ```Korrelasjonsid``` and ```Meldingsid``` headers should be logged on every request.
This is implemented by using a filter that will extract the values of these headers and putting them on 
[SLF4J MDC](http://www.slf4j.org/api/org/slf4j/MDC.html). The artifact implementing the filter is 
[aurora-header-mdc-filter](https://aurora/git/projects/AUF/repos/aurora-header-mdc-filter/browse) and is included
as a dependency in the pom.xml-file. See the ```ApplicationConfig```-class for details on how the filter is configured.

For details, see:
* [Logging og feilhåndtering - Krav](https://aurora/wiki/pages/viewpage.action?pageId=34578383)
* [UT-AUR-LOG-006](https://aurora/wiki/display/AURORA/UT-AUR-LOG-006)
* [UT-AUR-REST-004](https://aurora/wiki/display/AURORA/UT-AUR-REST-004)

 
## Database Migrations with Flyway

Databases are migrated using the database migration tool [Flyway](https://flywaydb.org/). Migrations should be put in
the ```src/main/resources/db/migration```-folder. Flyway is included as a dependency in the pom.xml-file and Spring
Boot automatically picks it up and configures it using the application DataSource.

For details, see:
* [Flyway SpringBoot](https://flywaydb.org/documentation/plugins/springboot)
* [Spring Doc: Use a higher-level database migration tool](https://docs.spring.io/spring-boot/docs/current/reference/html/howto-database-initialization.html#howto-execute-flyway-database-migrations-on-startup)


## Application Health

Application health is provided by standard Spring Boot features. An example is provided in the ```CounterHealth``` 
class.

For details, see:
* [Spring Doc: Health information](http://docs.spring.io/spring-boot/docs/current/reference/html/production-ready-endpoints.html#production-ready-health)


## Actuator Endpoints


## Metrics
HTTP Status, Logging, Standard Metrics, Prometheus


# Security


## Unit Testing with Spock

Unit testing has been set up and configured to use the Spock Framework with support for mocking classes and classes
with non-default constructors. The gmavenplus-plugin and surefire-plugin are configured so that the tests will run in
a standard maven build.

For details, see:
* [gmavenplus-plugin](http://groovy.github.io/GMavenPlus/)
* [Spock Framework](http://spockframework.org/)


## Documentation


## Build Configuration

### Leveransepakke

### Versioning

### Code Analysis
Checkstyle, Sonar, Jacoco, PiTest

### Build metadata

### Jenkinsfile

### Nexus IQ


## Openshift Integrations

### Build Metadata for Docker Images

### Aurora Console Integration


## Development Tools
