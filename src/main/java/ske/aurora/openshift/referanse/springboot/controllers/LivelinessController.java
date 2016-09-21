package ske.aurora.openshift.referanse.springboot.controllers;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LivelinessController {

    @RequestMapping("/ping")
    public HttpEntity<String> alive() {
        return new ResponseEntity<>("", HttpStatus.NO_CONTENT);
    }
}
