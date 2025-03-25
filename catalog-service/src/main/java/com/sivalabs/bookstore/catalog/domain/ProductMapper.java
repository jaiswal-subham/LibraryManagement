package com.sivalabs.bookstore.catalog.domain;

public class ProductMapper {

    Product convertProductEntityToProduct(ProductEntity pe) {
        return new Product(pe.getCode(), pe.getName(), pe.getDescription(), pe.getImageUrl(), pe.getPrice());
    }
}
