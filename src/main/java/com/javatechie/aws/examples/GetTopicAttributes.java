//package com.javatechie.aws.examples;
//
////snippet-start:[sns.java2.GetTopicAttributes.import]
//import com.amazonaws.auth.AWSStaticCredentialsProvider;
//import com.amazonaws.auth.BasicAWSCredentials;
//import com.amazonaws.services.sns.AmazonSNSClient;
//import com.amazonaws.services.sns.AmazonSNSClientBuilder;
//import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
//import software.amazon.awssdk.regions.Region;
//import software.amazon.awssdk.services.sns.SnsClient;
//import software.amazon.awssdk.services.sns.model.GetTopicAttributesRequest;
//import software.amazon.awssdk.services.sns.model.GetTopicAttributesResponse;
//import software.amazon.awssdk.services.sns.model.SnsException;
////snippet-end:[sns.java2.GetTopicAttributes.import]
//
//
//public class GetTopicAttributes {
//    public static void main(String[] args) {
//
////        final String usage = "\n" +
////                "Usage: " +
////                "   <topicArn>\n\n" +
////                "Where:\n" +
////                "   topicArn - The ARN of the topic to look up.\n\n";
//
////        if (args.length != 1) {
////            System.out.println(usage);
////            System.exit(1);
////        }
///*
//        return (AmazonSNSClient) AmazonSNSClientBuilder.standard()
//                .withRegion(region)
//                .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKey, secretKey)))
//                .build();
//
// */
//
////        String topicArn = args[0];
//        String topicArn = "arn:aws:sns:eu-west-3:026806867949:image-topic";
//        SnsClient snsClient = SnsClient.builder()
//                .region(Region.US_EAST_1)
//                .credentialsProvider(new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKey, secretKey)))
//                .build();
//
//        System.out.println("Getting attributes for a topic with name: " + topicArn);
//        getSNSTopicAttributes(snsClient, topicArn) ;
//        snsClient.close();
//    }
//
//    //snippet-start:[sns.java2.GetTopicAttributes.main]
//    public static void getSNSTopicAttributes(SnsClient snsClient, String topicArn ) {
//
//        try {
//            GetTopicAttributesRequest request = GetTopicAttributesRequest.builder()
//                    .topicArn(topicArn)
//                    .build();
//
//            GetTopicAttributesResponse result = snsClient.getTopicAttributes(request);
//            System.out.println("\n\nStatus is " + result.sdkHttpResponse().statusCode() + "\n\nAttributes: \n\n" + result.attributes());
//
//        } catch (SnsException e) {
//            System.err.println(e.awsErrorDetails().errorMessage());
//            System.exit(1);
//        }
//    }
//    //snippet-end:[sns.java2.GetTopicAttributes.main]
//}