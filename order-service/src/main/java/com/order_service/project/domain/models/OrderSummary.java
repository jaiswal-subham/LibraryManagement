package com.order_service.project.domain.models;

public class OrderSummary {
    private String orderNumber;
    private OrderStatus status;

    public OrderSummary(String orderNumber, OrderStatus status) {
        this.orderNumber = orderNumber;
        this.status = status;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public OrderStatus getStatus() {
        return status;
    }
}

