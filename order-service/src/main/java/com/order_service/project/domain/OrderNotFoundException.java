/* (C)2025 */
package com.order_service.project.domain;

public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException(String message) {
        super(message);
    }

    public static OrderNotFoundException forOrderNumber(String orderNumber) {
        return new OrderNotFoundException("order with number" + orderNumber + "is not found");
    }
}
