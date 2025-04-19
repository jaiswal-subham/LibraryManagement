package com.order_service.project.domain;

import java.util.List;


public record OrderResponse(String userName, List<OrderItem> orderItem, String customerName, String customerEmail,
                            String customerPhone,
                            String deliveryAddressLine1, String deliveryAddressLine2, String deliveryAddressCity,
                            String deliveryAddressState, String deliveryAddressZipCode, com.order_service.project.domain.models.OrderStatus status,
                            String comments) {
}










