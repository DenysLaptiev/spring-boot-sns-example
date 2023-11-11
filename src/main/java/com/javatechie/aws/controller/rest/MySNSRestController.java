package com.javatechie.aws.controller.rest;

import com.javatechie.aws.service.MySNSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MySNSRestController {

    private final MySNSService service;

    @Autowired
    public MySNSRestController(MySNSService service) {
        this.service = service;
    }

    @GetMapping("/add-subscription/{email}")
    public String addSubscribtion(@PathVariable String email) {
        return service.addSubscription(email);
    }

    @GetMapping("/send-notification")
    public String publishMessageToTopic() {
        return service.publishMessageToTopic();
    }
}
