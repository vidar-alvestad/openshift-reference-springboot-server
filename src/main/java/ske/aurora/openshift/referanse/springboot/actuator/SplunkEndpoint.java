package ske.aurora.openshift.referanse.springboot.actuator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.endpoint.mvc.AbstractMvcEndpoint;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

@Component
public class SplunkEndpoint extends AbstractMvcEndpoint {

    private final String namespace;
    private final String appName;

    @Autowired
    public SplunkEndpoint(
        @Value("${pod.namespace:}") String namespace,
        @Value("${app.name:}") String appName) {
        super("/splunk", false);
        this.namespace = namespace;
        this.appName = appName;
    }

    @GetMapping()
    public String redirect() {

        return
            "redirect:http://splunk.skead.no/en-US/app/search/search?q=search environment%3D" + namespace
                + " application%3D" + appName + " sourcetype%3Dlog4j&display.page.search.mode=smart&dispatch"
                + ".sample_ratio=1&earliest=-10m&latest=now";

    }
}
