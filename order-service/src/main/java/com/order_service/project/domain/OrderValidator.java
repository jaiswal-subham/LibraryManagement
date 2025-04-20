/* (C)2025 */
package com.order_service.project.domain;

import com.order_service.project.client.Product;
import com.order_service.project.client.ProductServiceClient;
import com.order_service.project.domain.models.CreateOrderRequest;
import com.order_service.project.web.exception.InvalidOrderException;
import java.util.Set;
import org.springframework.stereotype.Component;

@Component
public class OrderValidator {

    ProductServiceClient productServiceClient;

    public OrderValidator(ProductServiceClient productServiceClient) {
        this.productServiceClient = productServiceClient;
    }

    void validate(CreateOrderRequest request) throws InvalidOrderException {
        Set<OrderItem> orderItems = request.getItems();
        for (OrderItem orderItem : orderItems) {
            Product p =
                    productServiceClient
                            .getProductByCode(String.valueOf(orderItem.code()))
                            .orElseThrow(
                                    () ->
                                            new InvalidOrderException(
                                                    "Invalid Product code" + orderItem.code()));

            if (p.getPrice().compareTo(orderItem.price()) != 0) {
                throw new InvalidOrderException("Product price is not matching");
            }
        }
    }
}
