/* (C)2025 */
package com.order_service.project.domain;

import com.order_service.project.domain.models.*;
import com.order_service.project.web.exception.InvalidOrderException;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class OrderService {

    OrderRepository orderRepository;
    OrderValidator orderValidator;
    OrderEventService orderEventService;

    public OrderService(
            OrderRepository orderRepository,
            OrderValidator orderValidator,
            OrderEventService orderEventService) {
        this.orderRepository = orderRepository;
        this.orderValidator = orderValidator;
        this.orderEventService = orderEventService;
    }

    public Optional<OrderSummary> getOrdersByUser(String userName) {
        return this.orderRepository.findByUserName(userName);
    }

    public static String generateRandomNumber() {
        Random random = new Random();
        int number =
                100000 + random.nextInt(900000); // Generates a number between 100000 and 999999
        return String.valueOf(number);
    }

    public CreateOrderResponse createOrder(String userName, CreateOrderRequest request)
            throws InvalidOrderException {
        orderValidator.validate(request);
        String orderNumber = generateRandomNumber();
        OrderEntity orderEntity =
                this.orderRepository.save(
                        new OrderMapper().orderRequestToOrderResponse(request, orderNumber));

        // Save Event into events DB
        this.orderEventService.save(OrderEventMapper.buildOrderCreatedEvent(orderEntity));
        return new CreateOrderResponse(orderNumber);
    }

    public void processOrders() {
        List<OrderEntity> newOrders = this.orderRepository.getNewOrders();

        for (OrderEntity oe : newOrders) {
            try {
                if (Objects.equals(oe.getDeliveryAddressCountry(), "INDIA")
                        || Objects.equals(oe.getDeliveryAddressCountry(), "USA")) {

                    oe.setStatus(OrderStatus.DELIVERED);
                    this.orderRepository.save(oe);
                    this.orderEventService.save(OrderEventMapper.buildOrderDeliveredEvent(oe));

                } else {
                    oe.setStatus(OrderStatus.CANCELLED);
                    this.orderRepository.save(oe);
                    this.orderEventService.save(OrderEventMapper.buildOrderCancelledEvent(oe));
                }

            } catch (Exception e) {
                oe.setStatus(OrderStatus.ERROR);
                this.orderRepository.save(oe);
                this.orderEventService.save(OrderEventMapper.buildOrderErrorEvent(oe));
                // Optionally log the error
                // log.error("Failed to process order: " + oe.getId(), e);
            }
        }
    }
}
