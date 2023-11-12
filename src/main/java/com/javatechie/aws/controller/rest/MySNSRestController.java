package com.javatechie.aws.controller.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javatechie.aws.model.Message;
import com.javatechie.aws.model.NotificationRequest;
import com.javatechie.aws.model.SNSResponseBody;
import com.javatechie.aws.service.MySNSService;
import com.javatechie.aws.service.SNSMessageSender;
import jdk.jfr.ContentType;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@Slf4j
public class MySNSRestController {

    private final MySNSService service;
    private final SNSMessageSender snsMessageSender;

    @Autowired
    public MySNSRestController(MySNSService service, SNSMessageSender snsMessageSender) {
        this.service = service;
        this.snsMessageSender = snsMessageSender;
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

    @PostMapping("/publish-with-sender")
    public String publishToTopicWithSender(@RequestBody NotificationRequest notificationRequest) {
        log.info("topicName=" + notificationRequest.getTopicName());
        log.info("message=" + notificationRequest.getMessage());
        log.info("subject=" + notificationRequest.getSubject());
        snsMessageSender.send(notificationRequest.getTopicName(), notificationRequest.getMessage(), notificationRequest.getSubject());
        return notificationRequest.getMessage();
    }


    /*
    For example, we can subscribe to a topic "image-topic" by calling the URL:
        https://host:port/image-topic/

{
    "message":"https://sns-service.eu-west-3.elasticbeanstalk.com/image-topic/"
}


     */
    @PostMapping("/add-https-subscription")
    public String subscribeHTTPSToTopic(@RequestBody Message message) {
        log.info("---> MySNSRestController: subscripeHTTPSToTopic: message=" + message.getMessage());
        return service.subscribeHTTPSToTopic(message.getMessage());
    }

    @SneakyThrows
    @PostMapping(value = "/receive")
    public ResponseEntity<?> receiveMessageFromTopic(@RequestBody String json, @RequestHeader MultiValueMap<String, String> headers) {
        log.info("---> MySNSRestController: receiveMessageFromTopic: json=" + json);

        Map<String, String> messageHeaders = new HashMap<>();
        Map<String, String> messageFields = new HashMap<>();


        headers.forEach((key, value) -> {

            String joinedStringValue = value.stream().collect(Collectors.joining("|"));

            messageHeaders.put(key, joinedStringValue);

            log.info(String.format(
                    "Header '%s' = %s", key, joinedStringValue));
        });

        messageFields = new ObjectMapper().readValue(
                json.getBytes(),
                Map.class);

        SNSResponseBody responseBody = new SNSResponseBody(messageHeaders, messageFields);
        return new ResponseEntity<>(
                responseBody, HttpStatus.OK);
    }
}
