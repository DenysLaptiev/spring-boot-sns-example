package com.javatechie.aws.config;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.core.NotificationMessagingTemplate;
import org.springframework.cloud.aws.messaging.endpoint.NotificationStatusHandlerMethodArgumentResolver;
import org.springframework.cloud.aws.messaging.endpoint.config.NotificationHandlerMethodArgumentResolverFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import software.amazon.awssdk.auth.credentials.*;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sns.SnsClient;

import java.util.List;

@Configuration
public class AWSSNSConfig {
    @Value("${cloud.aws.region.static}")
    private String region;

    @Value("${cloud.aws.credentials.access-key}")
    private String accessKey;

    @Value("${cloud.aws.credentials.secret-key}")
    private String secretKey;


    @Primary
    @Bean
    public AmazonSNSClient amazonSnsClient() {
        return (AmazonSNSClient) AmazonSNSClientBuilder.standard()
                .withRegion(region)
                .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKey, secretKey)))
                .build();
    }

    @Bean
    public NotificationMessagingTemplate notificationMessagingTemplate(AmazonSNSClient amazonSNS) {
        return new NotificationMessagingTemplate(amazonSNS);
    }

    /*
    @Bean
    public SnsClient snsClient() {

        return SnsClient.builder()
                .region(Region.EU_WEST_3)
                .credentialsProvider(ProfileCredentialsProvider.create())
                .build();
    }
     */

    @Bean
    public SnsClient snsClient() {

        AwsBasicCredentials awsCredentials = AwsBasicCredentials.create(accessKey, secretKey);

        return SnsClient.builder()
                .region(Region.EU_WEST_3)
                .credentialsProvider(StaticCredentialsProvider.create(awsCredentials))
                .build();
    }
}
