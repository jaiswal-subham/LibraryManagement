package com.order_service.project.client;

import com.order_service.project.ApplicationProperties;
import org.springframework.boot.http.client.ClientHttpRequestFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

import java.time.Duration;

@Configuration
public class CatalogServiceClientConfig {

    private final ApplicationProperties applicationProperties;

    public CatalogServiceClientConfig(ApplicationProperties applicationProperties) {
        this.applicationProperties = applicationProperties;
    }

    @Bean
    public RestClient restClient() {
        return RestClient.builder()
                .baseUrl(applicationProperties.catalogServiceUrl()) // using record accessor
                .requestFactory(ClientHttpRequestFactoryBuilder.simple()
                        .withCustomizer(customizer -> {
                            customizer.setConnectTimeout(Duration.ofSeconds(5));
                            customizer.setReadTimeout(Duration.ofSeconds(5));
                        })
                        .build()).build();
    }


}


