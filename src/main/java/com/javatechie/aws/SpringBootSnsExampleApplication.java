package com.javatechie.aws;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.aws.autoconfigure.context.ContextRegionProviderAutoConfiguration;
import org.springframework.cloud.aws.autoconfigure.context.ContextStackAutoConfiguration;

@SpringBootApplication(exclude = {ContextStackAutoConfiguration.class, ContextRegionProviderAutoConfiguration.class})
public class SpringBootSnsExampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootSnsExampleApplication.class, args);
    }

}
