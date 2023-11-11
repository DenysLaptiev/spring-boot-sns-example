package com.javatechie.aws.controller;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.aws.messaging.config.annotation.NotificationMessage;
import org.springframework.cloud.aws.messaging.config.annotation.NotificationSubject;
import org.springframework.cloud.aws.messaging.endpoint.NotificationStatus;
import org.springframework.cloud.aws.messaging.endpoint.annotation.NotificationMessageMapping;
import org.springframework.cloud.aws.messaging.endpoint.annotation.NotificationSubscriptionMapping;
import org.springframework.cloud.aws.messaging.endpoint.annotation.NotificationUnsubscribeConfirmationMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/topic-subscriber")//image-topic - is the name of the topic
@Slf4j
public class SNSEndpointController {

    @NotificationMessageMapping
    public void receiveNotification(@NotificationMessage String message, @NotificationSubject String subject) {
        log.info("---> Received message: {}, having subject: {}", message, subject);
        log.info("---> Received message: {}, having subject: {}", message, subject);
        log.info("---> Received message: {}, having subject: {}", message, subject);
    }

    @NotificationUnsubscribeConfirmationMapping
    public void confirmSubscriptionMessage(NotificationStatus notificationStatus) {
        log.info("---> Unsubscribed from Topic");
        log.info("---> Unsubscribed from Topic");
        log.info("---> Unsubscribed from Topic");
        notificationStatus.confirmSubscription();
    }

    @NotificationSubscriptionMapping
    public void confirmUnsubscribeMessage(NotificationStatus notificationStatus) {
        log.info("---> Subscribed to Topic");
        log.info("---> Subscribed to Topic");
        log.info("---> Subscribed to Topic");
        notificationStatus.confirmSubscription();
    }
}