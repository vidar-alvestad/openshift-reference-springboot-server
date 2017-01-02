package ske.aurora.openshift.referanse.springboot.actuator;

import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.endpoint.mvc.AbstractMvcEndpoint;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

@Component
public class GrafanaEndpoint extends AbstractMvcEndpoint {

    private final String namespace;
    private final String appName;
    private final String routeName;

    @Autowired
    public GrafanaEndpoint(
        @Value("${pod.namespace:}") String namespace,
        @Value("${app.name:}") String appName,
        @Value("${route.name:}") String routeName) {
        super("/grafana", false);
        this.namespace = namespace;
        this.appName = appName;
        this.routeName = routeName;
    }

    private String findGrafanaDSFromRouteName(String routeName) {
        String[] parts = routeName.split(Pattern.quote("."));
        if(parts.length < 2){
            return "openshift-utv-ose";
        }
        return "openshift-" + parts[1] + "-ose";
    }

    @GetMapping()
    public String redirect() {

        String grafanaDS = findGrafanaDSFromRouteName(routeName);

        String grafanaUrl = String.format("redirect:http://metrics.skead"
            + ".no/dashboard/db/openshift-project-spring-actuator-view?var-ds=%s&var"
            + "-namespace=%s&var-app=%s", grafanaDS, namespace, appName);
        return grafanaUrl;
    }
}
