/* (C)2025 */
package com.order_service.project.domain;

import com.order_service.project.domain.models.CreateOrderRequest;
import com.order_service.project.domain.models.OrderStatus;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class OrderMapper {
    public OrderResponse orderEntityToOrderResponse(OrderEntity oe) {
        List<OrderItem> oi =
                oe.getItems().stream()
                        .map(
                                x ->
                                        new OrderItem(
                                                x.getCode(),
                                                x.getName(),
                                                x.getPrice(),
                                                x.getQuantity()))
                        .collect(Collectors.toList());
        return new OrderResponse(
                oe.getUserName(),
                oi,
                oe.getCustomerName(),
                oe.getCustomerEmail(),
                oe.getCustomerPhone(),
                oe.getDeliveryAddressLine1(),
                oe.getDeliveryAddressLine2(),
                oe.getDeliveryAddressCity(),
                oe.getDeliveryAddressState(),
                oe.getDeliveryAddressZipCode(),
                oe.getStatus(),
                oe.getComments());
    }

    public OrderEntity orderRequestToOrderResponse(CreateOrderRequest cor, String orderNumber) {
        OrderEntity orderEntity = new OrderEntity();
        Set<OrderItemEntity> orderItemEntityList = new LinkedHashSet<>();
        for (OrderItem orderItem : cor.getItems()) {
            OrderItemEntity orderItemEntity = new OrderItemEntity();
            orderItemEntity.setCode(orderItem.code());
            orderItemEntity.setName(orderItem.name());
            orderItemEntity.setPrice(orderItem.price());
            orderItemEntity.setQuantity(orderItem.quantity());
            orderItemEntity.setOrder(orderEntity);
            orderItemEntityList.add(orderItemEntity);
        }

        orderEntity.setUserName("user");
        orderEntity.setCustomerName(cor.getName());
        orderEntity.setCustomerEmail(cor.getEmail());
        orderEntity.setCustomerPhone(cor.getPhone());
        orderEntity.setDeliveryAddressLine1(cor.getAddressLine1());
        orderEntity.setDeliveryAddressLine2(cor.getAddressLine2());
        orderEntity.setDeliveryAddressCity(cor.getCity());
        orderEntity.setDeliveryAddressZipCode(cor.getZipCode());
        orderEntity.setDeliveryAddressState(cor.getState());
        orderEntity.setStatus(OrderStatus.NEW);
        orderEntity.setComments("Pass");
        orderEntity.setCreatedAt(LocalDateTime.now());
        orderEntity.setUpdatedAt(LocalDateTime.now());
        orderEntity.setItems(orderItemEntityList);
        orderEntity.setOrderNumber(orderNumber);
        orderEntity.setDeliveryAddressCountry(cor.getCountry());

        return orderEntity;
    }
}
