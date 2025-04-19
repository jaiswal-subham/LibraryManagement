package com.order_service.project.domain.models;

import com.order_service.project.domain.OrderItem;

import java.util.Set;

public class OrderDTO {
    String orderNumber;
    String user;
    Set<OrderItem> items;
    OrderStatus orderStatus;
}
