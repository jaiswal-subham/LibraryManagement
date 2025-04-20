package com.sivalabs.notification_service.events;

import com.sivalabs.notification_service.domain.NotificationService;
import com.sivalabs.notification_service.domain.OrderEventEntity;
import com.sivalabs.notification_service.domain.OrderEventRepository;
import com.sivalabs.notification_service.domain.models.OrderCancelledEvent;
import com.sivalabs.notification_service.domain.models.OrderCreatedEvent;
import com.sivalabs.notification_service.domain.models.OrderDeliveredEvent;
import com.sivalabs.notification_service.domain.models.OrderErrorEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class OrderEventHandler {
    private static final Logger log = LoggerFactory.getLogger(OrderEventHandler.class);

    private final NotificationService notificationService;
    private final OrderEventRepository orderEventRepository;

    public OrderEventHandler(NotificationService notificationService, OrderEventRepository orderEventRepository) {
        this.notificationService = notificationService;
        this.orderEventRepository = orderEventRepository;
    }

    @RabbitListener(queues = "${notifications.new-orders-queue}")
    public void handle(OrderCreatedEvent event) {
        if (orderEventRepository.existsByEventId(event.getEventId())) {
            log.warn("Received duplicate OrderCreatedEvent with eventId: {}", event.getEventId());
            return;
        }
        log.info("Received a OrderCreatedEvent with orderNumber:{}: ", event.getOrderNumber());
        notificationService.sendOrderCreatedNotification(event);
        var orderEvent = new OrderEventEntity(event.getEventId());
        orderEventRepository.save(orderEvent);
    }

    @RabbitListener(queues = "${notifications.delivered-orders-queue}")
    public void handle(OrderDeliveredEvent event) {
        if (orderEventRepository.existsByEventId(event.getEventId())) {
            log.warn("Received duplicate OrderDeliveredEvent with eventId: {}", event.getEventId());
            return;
        }
        log.info("Received a OrderDeliveredEvent with orderNumber:{}: ", event.getOrderNumber());
        notificationService.sendOrderDeliveredNotification(event);
        var orderEvent = new OrderEventEntity(event.getEventId());
        orderEventRepository.save(orderEvent);
    }

    @RabbitListener(queues = "${notifications.cancelled-orders-queue}")
    public void handle(OrderCancelledEvent event) {
        if (orderEventRepository.existsByEventId(event.getEventId())) {
            log.warn("Received duplicate OrderCancelledEvent with eventId: {}", event.getEventId());
            return;
        }
        notificationService.sendOrderCancelledNotification(event);
        log.info("Received a OrderCancelledEvent with orderNumber:{}: ", event.getOrderNumber());
        var orderEvent = new OrderEventEntity(event.getEventId());
        orderEventRepository.save(orderEvent);
    }

    @RabbitListener(queues = "${notifications.error-orders-queue}")
    public void handle(OrderErrorEvent event) {
        if (orderEventRepository.existsByEventId(event.getEventId())) {
            log.warn("Received duplicate OrderErrorEvent with eventId: {}", event.getEventId());
            return;
        }
        log.info("Received a OrderErrorEvent with orderNumber:{}: ", event.getOrderNumber());
        notificationService.sendOrderErrorEventNotification(event);
        OrderEventEntity orderEvent = new OrderEventEntity(event.getEventId());
        orderEventRepository.save(orderEvent);
    }
}
