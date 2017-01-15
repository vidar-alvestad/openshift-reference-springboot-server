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


# How to Use the Application

As the intention of the Reference Application is to serve as a guide to how to set up your own application, it is not 
intended to be used directly as a starting point for new applications without any modifications.

You may fork the repository of the application to be able to apply new commits onto your own modified application, or
your may export the code into your own fresh repository and manually keep up to date with changes to the central
repository by inspecting the change logs. Which approach is best will depend on the amount of changes you make to 
central files, like the ```pom.xml``` and ```application.yml```.

Regardless of the approach you use to keep up with changes, you will have to make the following changes to the
fork/export:

 * change the ```groupId``` and ```artifactId``` in the ```pom.xml``` to match that of your application
 * rename the main package in ```src/main``` to match that of your application
 * change the ```info.application.name``` property in ```application.yml``` to match that of your application
 * change the ```info.links``` in ```application.yml``` to match that of your application
 * remove the example database code (migrations under ```src/main/resource/db/migrations```), the Counter-classes in the controllers, health and service packages and the database config in ```application.yml```


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


## HTTP Endpoint JSON serialization

The format for Dates in JSON responses has been set to com.fasterxml.jackson.databind.util.ISO8601DateFormat in
preference of the default format which is just milli seconds since Epoc.


## Actuator Endpoints

Spring Boot Actuator is included in the application, but most endpoints are disabled by default. See the 
```resources/application.yml``` file for more details.


## Metrics
HTTP Status, Logging, Standard Metrics, Prometheus


## Security


## Unit Testing with Spock

Unit testing has been set up and configured to use the Spock Framework with support for mocking classes and classes
with non-default constructors. The gmavenplus-plugin and surefire-plugin are configured so that the tests will run in
a standard maven build.

For details, see:
* [gmavenplus-plugin](http://groovy.github.io/GMavenPlus/)
* [Spock Framework](http://spockframework.org/)


## Documentation

For documentation, the Reference Application configured to use [spring-rest-docs](https://projects.spring.io/spring-restdocs/) 
which is an approach to documentation that combines hand-written documentation with auto-generated snippets produced 
with Spring MVC Test. Please read the spring-rest-docs documentation for an overview of how the technology works.

The ```pom.xml``` is set up with the necessary plugins to build the documentation. This is basically including
spring-rest-docs on the test classpath to generate snippets and configuring the asciidoctor-maven-plugin to process
documentation in the ```src/main/asciidoc```-folder and the output folder of the spring-rest-docs tests. The 
maven-resources-plugin is configured to include the generated documentation in ```/static/docs``` in the final jar
which results in spring-boot exposing the files over HTTP on ```/docs``` (see [Static Content](http://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-developing-web-applications.html#boot-features-spring-mvc-static-content)
from the spring-boot documentation for details).

A class, ```AbstractControllerTest```, is included as an example base class for tests that use spring-rest-docs.


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
