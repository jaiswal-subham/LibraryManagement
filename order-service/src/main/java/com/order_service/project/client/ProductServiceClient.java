package com.order_service.project.client;

import io.github.resilience4j.retry.annotation.Retry;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;


import java.util.Optional;

@Component
public class ProductServiceClient {

    RestClient restClient;

    public ProductServiceClient(RestClient restClient) {
        this.restClient = restClient;
    }

    @Retry(name = "catalog-service", fallbackMethod = "getProductByCodeFallBack")
    public Optional<Product> getProductByCode(String code) {

        Product product = restClient.get()
                .uri("/api/products/{code}", code)
                .retrieve()
                .body(Product.class);

        return Optional.ofNullable(product);


    }

    public Optional<Product> getProductByCodeFallBack(String code, Throwable t) {
        System.out.println("Fall back");
        return Optional.empty();
    }


}
