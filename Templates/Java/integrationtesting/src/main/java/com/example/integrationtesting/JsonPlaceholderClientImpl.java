package com.example.integrationtesting;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class JsonPlaceholderClientImpl implements JsonPlaceholderClient {

    private final RestClient client;
    public JsonPlaceholderClientImpl(@Value("${httpClientSettings.baseUrl}") String baseUrl) {
        client = RestClient.builder().baseUrl(baseUrl).build();
    }

    public String get(String path, Optional<String> queryString) {
        var url = path + queryString.orElse("");
        var response = client.get().uri(url).retrieve().body(String.class);
        return response;
    }
}
