/* (C)2025 */
package com.order_service.project.web.controllers;

import com.order_service.project.domain.OrderNotFoundException;
import com.order_service.project.domain.OrderService;
import com.order_service.project.domain.models.CreateOrderRequest;
import com.order_service.project.domain.models.CreateOrderResponse;
import com.order_service.project.domain.models.OrderSummary;
import com.order_service.project.web.exception.InvalidOrderException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/orders")
class OrderController {

    OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public OrderSummary getOrdersByUser() {
        String userName = "user";
        return this.orderService
                .getOrdersByUser(userName)
                .orElseThrow(() -> new OrderNotFoundException("Order For this user not found"));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    CreateOrderResponse createOrder(@Valid @RequestBody CreateOrderRequest request)
            throws InvalidOrderException {
        String userName = "user";
        return orderService.createOrder(userName, request);
    }
}
