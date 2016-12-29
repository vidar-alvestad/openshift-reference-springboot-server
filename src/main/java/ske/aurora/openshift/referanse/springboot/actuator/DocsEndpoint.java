package ske.aurora.openshift.referanse.springboot.actuator;

import org.springframework.boot.actuate.endpoint.mvc.AbstractMvcEndpoint;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

@Component
public class DocsEndpoint extends AbstractMvcEndpoint {

    public DocsEndpoint() {
        super("/docs", false);
    }

    @GetMapping()
    public String redirectTodocs() {
        return "redirect:/docs/index.html";
    }
}
