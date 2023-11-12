package com.javatechie.aws.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Map;

@Data
public class SNSRequestBody {

    private Map<String, String> bodyMap;

    @JsonCreator
    public SNSRequestBody(@JsonProperty("bodyMap") Map<String, String> bodyMap) {
        this.bodyMap = bodyMap;
    }
}
