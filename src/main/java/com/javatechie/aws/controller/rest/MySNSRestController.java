package com.javatechie.aws.controller.rest;

import com.javatechie.aws.model.Message;
import com.javatechie.aws.service.MySNSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    //new functions

    @GetMapping("/topic-attributes")
    public String getTopicAttributes() {
        return service.getSNSTopicAttributes();
    }

    @GetMapping("/topics")
    public String getTopicsList() {
        return service.listSNSTopics();
    }

    @GetMapping("/subscriptions")
    public String getSubscriptionsList() {
        return service.listSubscriptions();
    }

    //!!!!!!!!!!!
    @PostMapping("/publish")
    public String publishToTopic(@RequestBody Message message) {
        return service.publishToTopic(message.getMessage());
    }

    @PostMapping("/add-https-subscription")
    public String subscripeHTTPSToTopic(@RequestBody Message message) {
        return service.subscribeHTTPSToTopic(message.getMessage());
    }

    @PostMapping("/receive")
    public String receiveMessageFromTopic(@RequestBody Object message) {
        System.out.println(message);
        return message.toString();
    }
}
