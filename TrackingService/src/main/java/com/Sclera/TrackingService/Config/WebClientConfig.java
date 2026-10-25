package com.Sclera.TrackingService.Config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Value("${package-service.base-url}")
    private String packageServiceBaseUrl;

    @Bean
    public WebClient packageServiceWebClient() {
        return WebClient.builder()
                .baseUrl(packageServiceBaseUrl)
                .build();
    }
}