package com.order_service.project.domain;

import com.order_service.project.domain.models.*;
import com.order_service.project.publisher.OrderEventPublisher;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

@Service
public class OrderEventService {
    OrderEventRepository orderEventRepository;
    ObjectMapper objectMapper;
    OrderEventPublisher orderEventPublisher;

    public OrderEventService(OrderEventRepository orderEventRepository, ObjectMapper objectMapper, OrderEventPublisher orderEventPublisher) {
        this.orderEventRepository = orderEventRepository;
        this.objectMapper = objectMapper;
        this.orderEventPublisher = orderEventPublisher;
    }

    void save(OrderCreatedEvent event) {
        OrderEventEntity orderEvent = new OrderEventEntity();
        orderEvent.setEventId(event.getEventId());
        orderEvent.setEventType(OrderEventType.ORDER_CREATED);
        orderEvent.setOrderNumber(event.getOrderNumber());
        orderEvent.setCreatedAt(event.getCreatedAt());
        orderEvent.setPayload(toJsonPayload(event));
        this.orderEventRepository.save(orderEvent);
    }

    void save(OrderDeliveredEvent event) {
        OrderEventEntity orderEvent = new OrderEventEntity();
        orderEvent.setEventId(event.getEventId());
        orderEvent.setEventType(OrderEventType.ORDER_DELIVERED);
        orderEvent.setOrderNumber(event.getOrderNumber());
        orderEvent.setCreatedAt(event.getCreatedAt());
        orderEvent.setPayload(toJsonPayload(event));
        this.orderEventRepository.save(orderEvent);
    }


    void save(OrderCancelledEvent event) {
        OrderEventEntity orderEvent = new OrderEventEntity();
        orderEvent.setEventId(event.getEventId());
        orderEvent.setEventType(OrderEventType.ORDER_CANCELLED);
        orderEvent.setOrderNumber(event.getOrderNumber());
        orderEvent.setCreatedAt(event.getCreatedAt());
        orderEvent.setPayload(toJsonPayload(event));
        this.orderEventRepository.save(orderEvent);
    }

    void save(OrderErrorEvent event) {
        OrderEventEntity orderEvent = new OrderEventEntity();
        orderEvent.setEventId(event.getEventId());
        orderEvent.setEventType(OrderEventType.ORDER_PROCESSING_FAILED);
        orderEvent.setOrderNumber(event.getOrderNumber());
        orderEvent.setCreatedAt(event.getCreatedAt());
        orderEvent.setPayload(toJsonPayload(event));
        this.orderEventRepository.save(orderEvent);
    }


    public void publishOrderEvents() {
        List<OrderEventEntity> orderEvents = this.orderEventRepository.findAll(Sort.by("CreatedAt"));
        for (OrderEventEntity oe : orderEvents) {
            // publish the message into rabbit MQ // Rabbit mq should be idempotent
            this.publishEvent(oe);
            this.orderEventRepository.deleteById(oe.getId());
        }

    }

    private void publishEvent(OrderEventEntity event) {
        OrderEventType eventType = event.getEventType();
        switch (eventType) {
            case ORDER_CREATED:
                OrderCreatedEvent orderCreatedEvent = fromJsonPayload(event.getPayload(), OrderCreatedEvent.class);
                orderEventPublisher.publishOrderCreatedEvent(orderCreatedEvent);
                break;

            case ORDER_DELIVERED:
                OrderDeliveredEvent orderDeliveredEvent = fromJsonPayload(event.getPayload(), OrderDeliveredEvent.class);
                orderEventPublisher.publishOrderDeliveredEvent(orderDeliveredEvent);
                break;

            case ORDER_CANCELLED:
                OrderCancelledEvent orderCancelledEvent = fromJsonPayload(event.getPayload(), OrderCancelledEvent.class);
                orderEventPublisher.publishOrderCancelledEvent(orderCancelledEvent);
                break;

            case ORDER_PROCESSING_FAILED:
                OrderErrorEvent orderErrorEvent = fromJsonPayload(event.getPayload(), OrderErrorEvent.class);
                orderEventPublisher.publishOrderErrorEvent(orderErrorEvent);
                break;
            default:

        }
    }


    private String toJsonPayload(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private <T> T fromJsonPayload(String json, Class<T> type) {
        try {
            return objectMapper.readValue(json, type);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}