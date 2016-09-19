package ske.aurora.openshift.referanse.test;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LivelinessController {

    @RequestMapping(ApplicationConstants.PING_URL)
    public HttpEntity<String> alive() {
        return new ResponseEntity<>("Aplication is alive", HttpStatus.OK);
    }
}
