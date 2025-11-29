package com.mpezzanera.app.whather_app.bean;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

    @Value("${openweathermap.api.url}")
    private String apiBaseUrl;

    @Value("${openweathermap.api.key}")
    private String apiKey;

    @Bean
    @SuppressWarnings("null")
    public RestClient restClient() {
        if (!StringUtils.hasLength(apiBaseUrl) || (apiBaseUrl != null && apiBaseUrl.trim().isEmpty())) {
            throw new IllegalArgumentException("API base URL must not be null or empty");
        }

        return RestClient.builder()
                .baseUrl(apiBaseUrl)
                .defaultUriVariables(Map.of("appId", apiKey))
                .build();
    }
}
