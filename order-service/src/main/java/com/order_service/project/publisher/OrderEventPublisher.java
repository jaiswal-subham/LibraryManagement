/* (C)2025 */
package com.order_service.project.publisher;

import com.order_service.project.ApplicationProperties;
import com.order_service.project.domain.models.OrderCancelledEvent;
import com.order_service.project.domain.models.OrderCreatedEvent;
import com.order_service.project.domain.models.OrderDeliveredEvent;
import com.order_service.project.domain.models.OrderErrorEvent;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class OrderEventPublisher {

    ApplicationProperties applicationProperties;

    private final RabbitTemplate rabbitTemplate;

    public OrderEventPublisher(
            ApplicationProperties applicationProperties, RabbitTemplate rabbitTemplate) {
        this.applicationProperties = applicationProperties;
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publishOrderCreatedEvent(OrderCreatedEvent message) {
        System.out.println("Sending message to RabbitMQ OrderCreatedEvent" + message);
        rabbitTemplate.convertAndSend(
                applicationProperties.orderEventsExchange(),
                applicationProperties.newOrdersQueue(),
                message);
        System.out.println("Message sent successfully!");
    }

    public void publishOrderDeliveredEvent(OrderDeliveredEvent message) {
        System.out.println("Sending message to RabbitMQ OrderDeliveredEvent" + message);
        rabbitTemplate.convertAndSend(
                applicationProperties.orderEventsExchange(),
                applicationProperties.deliveredOrdersQueue(),
                message);
        System.out.println("Message sent successfully!");
    }

    public void publishOrderCancelledEvent(OrderCancelledEvent message) {
        System.out.println("Sending message to RabbitMQ OrderCancelledEvent" + message);
        rabbitTemplate.convertAndSend(
                applicationProperties.orderEventsExchange(),
                applicationProperties.cancelledOrdersQueue(),
                message);
        System.out.println("Message sent successfully!");
    }

    public void publishOrderErrorEvent(OrderErrorEvent message) {
        System.out.println("Sending message to RabbitMQ OrderCancelledEvent" + message);
        rabbitTemplate.convertAndSend(
                applicationProperties.orderEventsExchange(),
                applicationProperties.errorOrdersQueue(),
                message);
    }
}
