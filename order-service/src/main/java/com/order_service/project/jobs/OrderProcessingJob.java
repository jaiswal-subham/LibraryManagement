/* (C)2025 */
package com.order_service.project.jobs;

import com.order_service.project.domain.OrderService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class OrderProcessingJob {

    private final OrderService orderService;

    OrderProcessingJob(OrderService orderService) {
        this.orderService = orderService;
    }

    @Scheduled(cron = "${orders.process-orders-job-cron}")
    public void processOrders() {
        this.orderService.processOrders();
    }
}
