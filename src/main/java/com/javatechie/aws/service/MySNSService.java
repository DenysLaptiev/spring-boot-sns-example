package com.javatechie.aws.service;

import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.SubscribeRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MySNSService {

    public static final String PROTOCOL_NAME_EMAIL = "email";

    @Value("${cloud.aws.topic.arn}")
    private String topicArn;

    private final AmazonSNSClient snsClient;

    @Autowired
    public MySNSService(AmazonSNSClient snsClient) {
        this.snsClient = snsClient;
    }

    public String addSubscription(String email) {
        SubscribeRequest request = new SubscribeRequest(topicArn, PROTOCOL_NAME_EMAIL, email);
        snsClient.subscribe(request);
        return "Subscription request is pending. To confirm the subscription, check your email: " + email;
    }

    public String publishMessageToTopic() {
        PublishRequest request = new PublishRequest(topicArn, message(), subject());
        snsClient.publish(request);
        return "Notification sent successfully!";
    }

    private String message() {
        return "Dear Employee, this is a TEST";
    }

    private String subject() {
        return "Notification: Network connection issue";
    }
}
