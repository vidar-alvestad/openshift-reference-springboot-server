package ske.aurora.openshift.referanse.springboot.actuator;

import org.springframework.boot.actuate.endpoint.mvc.AbstractMvcEndpoint;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

@Component
public class PrometheusEndpoint extends AbstractMvcEndpoint {

    public PrometheusEndpoint() {
        super("/prometheus_metrics", false);
    }

    @GetMapping()
    public String redirectToPrometheus() {
        return "redirect:/prometheus";
    }
}

