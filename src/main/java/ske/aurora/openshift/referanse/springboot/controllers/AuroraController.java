package ske.aurora.openshift.referanse.springboot.controllers;

import static j2html.TagCreator.a;
import static j2html.TagCreator.body;
import static j2html.TagCreator.div;
import static j2html.TagCreator.h1;
import static j2html.TagCreator.h2;
import static j2html.TagCreator.head;
import static j2html.TagCreator.html;
import static j2html.TagCreator.li;
import static j2html.TagCreator.span;
import static j2html.TagCreator.title;
import static j2html.TagCreator.ul;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.info.BuildProperties;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuroraController {

    private final String applicationName;

    private final BuildProperties buildProperties;

    public AuroraController(BuildProperties buildProperties,
        @Value("${info.application.name}") String applicationName) {
        this.buildProperties = buildProperties;
        this.applicationName = applicationName;
    }

    @RequestMapping("/aurora")
    public String aurora() {

        System.out.println(buildProperties.getVersion());

        String html = html().with(
            head().with(
                title(applicationName)
            ),
            body().with(
                h1(applicationName),
                div().with(
                    h2("Imageversjon"),
                    span().withRel("imageVersion").withText(System.getenv("AURORA_VERSION")),
                    h2("Applikasjonsversjon"),
                    span().withRel("applicationVersion").withText(buildProperties.getVersion()),
                    h2("Tilhørighet"),
                    span().withRel("affiliation").withText("Aurora"),
                    h2("Links"),
                    ul().with(
                        li().with(a().withHref("/prometheus").withRel("prometheus").withText("Prometheus Metrics")),
                        li().with(a().withHref("/env").withRel("config").withText("Config").withType("actuator")),
                        li().with(a().withHref("/health").withRel("health").withText("Helse").withType("actuator")),
                        li().with(a().withHref("/info").withRel("info").withText("Info").withType("actuator")),
                        li().with(a().withHref("/ping").withRel("ping").withText("Ping")),
                        li().with(
                            a().withHref("/flyway").withRel("migration").withText("Database").withType("actuator"))
                    )
                )
            )
        ).render();

        return "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" \"http://www"
            + ".w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">\n" + html;
    }

}
