package ske.aurora.openshift.referanse.springboot.actuator;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.actuate.endpoint.mvc.AbstractMvcEndpoint;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Component
public class DependenciesEndpoint extends AbstractMvcEndpoint {

    public DependenciesEndpoint() {
        super("/dependencies", false);
    }

    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String, String> listDependencies() {


        /*
        Usikker på om dette i det hele tatt skal brukes eller hvordan det skal være.
         */
        return new HashMap<String, String>() {{
            put("boks", "http://foo.bar.baz/foobar");
        }};
    }

}
