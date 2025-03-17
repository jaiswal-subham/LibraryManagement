package com.sivalabs.bookstore.catalog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CatalogServiceApplication {
    public static void main(String[] args) {
        System.out.println("CatalogServiceApplication");
        SpringApplication.run(CatalogServiceApplication.class, args);
    }
}
