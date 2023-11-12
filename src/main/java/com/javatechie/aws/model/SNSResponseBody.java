package com.javatechie.aws.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Map;

@Data
public class SNSResponseBody {

    private Map<String, String> messageHeaders;
    private Map<String, String> messageFields;

    @JsonCreator
    public SNSResponseBody(@JsonProperty("messageHeaders") Map<String, String> messageHeaders, @JsonProperty("messageFields") Map<String, String> messageFields) {
        this.messageHeaders = messageHeaders;
        this.messageFields = messageFields;

    }
}
