package com.javatechie.aws.service;

import com.amazonaws.services.sns.AmazonSNSClient;
import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.services.sns.SnsClient;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.SubscribeRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sns.model.*;
import software.amazon.awssdk.services.sns.paginators.ListTopicsIterable;




@Service
@Slf4j
public class MySNSService {

    public static final String PROTOCOL_NAME_EMAIL = "email";

    @Value("${cloud.aws.topic.arn}")
    private String topicArn;

    private final AmazonSNSClient amazonSnsClient;
    private final SnsClient snsClient;

    @Autowired
    public MySNSService(AmazonSNSClient amazonSnsClient,SnsClient snsClient) {
        this.amazonSnsClient = amazonSnsClient;
        this.snsClient = snsClient;
    }

    public String addSubscription(String email) {
        SubscribeRequest request = new SubscribeRequest(topicArn, PROTOCOL_NAME_EMAIL, email);
        amazonSnsClient.subscribe(request);
        return "Subscription request is pending. To confirm the subscription, check your email: " + email;
    }

    public String publishMessageToTopic() {
        PublishRequest request = new PublishRequest(topicArn, message(), subject());
        amazonSnsClient.publish(request);
        return "Notification sent successfully!";
    }

    private String message() {
        return "Dear Employee, this is a TEST";
    }

    private String subject() {
        return "Notification: Network connection issue";
    }

    public String getSNSTopicAttributes() {
        try {
            GetTopicAttributesRequest request = GetTopicAttributesRequest.builder()
                    .topicArn(topicArn)
                    .build();

            GetTopicAttributesResponse result = snsClient.getTopicAttributes(request);
            System.out.println("\n\nStatus is " + result.sdkHttpResponse().statusCode() + "\n\nAttributes: \n\n" + result.attributes());

        } catch (SnsException e) {
            System.err.println(e.awsErrorDetails().errorMessage());
            System.exit(1);
        }
        return "result";
    }

    public String listSNSTopics() {
        try {
            ListTopicsIterable listTopics = snsClient.listTopicsPaginator();
            listTopics.stream()
                    .flatMap(r -> r.topics().stream())
                    .forEach(content -> System.out.println(" Topic ARN: " + content.topicArn()));

        } catch (SnsException e) {
            System.err.println(e.awsErrorDetails().errorMessage());
            System.exit(1);
        }
        return "result";
    }

    public String listSubscriptions() {
        try {
            ListSubscriptionsRequest request = ListSubscriptionsRequest.builder()
                    .build();

            ListSubscriptionsResponse result = snsClient.listSubscriptions(request);
            System.out.println(result.subscriptions());

        } catch (SnsException e) {

            System.err.println(e.awsErrorDetails().errorMessage());
            System.exit(1);
        }
        return "result";
    }

    //!!!!!!!!!!!
    public String publishToTopic(String message) {

        try {
            software.amazon.awssdk.services.sns.model.PublishRequest request = software.amazon.awssdk.services.sns.model.PublishRequest.builder()
                    .message(message)
                    .topicArn(topicArn)
                    .build();

            PublishResponse result = snsClient.publish(request);
            System.out.println(result.messageId() + " Message sent. Status is " + result.sdkHttpResponse().statusCode());

        } catch (SnsException e) {
            System.err.println(e.awsErrorDetails().errorMessage());
            System.exit(1);
        }
        return "result";
    }


    public String subscribeHTTPSToTopic(String url ) {

        try {
            software.amazon.awssdk.services.sns.model.SubscribeRequest request = software.amazon.awssdk.services.sns.model.SubscribeRequest.builder()
                    .protocol("https")
                    .endpoint(url)
                    .returnSubscriptionArn(true)
                    .topicArn(topicArn)
                    .build();
            log.info("---> request="+request);
            SubscribeResponse result = snsClient.subscribe(request);
            log.info("---> request="+request);
            System.out.println("Subscription ARN is " + result.subscriptionArn() + "\n\n Status is " + result.sdkHttpResponse().statusCode());

        } catch (SnsException e) {
            System.err.println(e.awsErrorDetails().errorMessage());
            System.exit(1);
        }
        return "result";
    }
}
