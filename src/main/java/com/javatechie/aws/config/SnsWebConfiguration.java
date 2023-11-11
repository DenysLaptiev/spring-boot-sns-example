package com.javatechie.aws.config;

import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClient;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.aws.context.annotation.ConditionalOnClass;
import org.springframework.cloud.aws.messaging.endpoint.NotificationStatusHandlerMethodArgumentResolver;
import org.springframework.cloud.aws.messaging.endpoint.config.NotificationHandlerMethodArgumentResolverFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

import static org.springframework.cloud.aws.messaging.endpoint.config.NotificationHandlerMethodArgumentResolverConfigurationUtils.getNotificationHandlerMethodArgumentResolver;

/**
 * @author Agim Emruli
 */
@Configuration
@ConditionalOnClass("org.springframework.web.servlet.config.annotation.WebMvcConfigurer")
public class SnsWebConfiguration extends WebMvcConfigurerAdapter {

    @Autowired
    private AmazonSNSClient amazonSns;

    @Bean
    public NotificationStatusHandlerMethodArgumentResolver notificationStatusHandlerMethodArgumentResolver() {
        return new NotificationStatusHandlerMethodArgumentResolver(amazonSns);
    }

    @Bean
    public NotificationHandlerMethodArgumentResolverFactoryBean notificationHandlerMethodArgumentResolverFactoryBean() {
        return new NotificationHandlerMethodArgumentResolverFactoryBean(amazonSns);
    }

    @Override
    @SneakyThrows
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(notificationStatusHandlerMethodArgumentResolver());
        resolvers.add(notificationHandlerMethodArgumentResolverFactoryBean().getObject());
    }
}