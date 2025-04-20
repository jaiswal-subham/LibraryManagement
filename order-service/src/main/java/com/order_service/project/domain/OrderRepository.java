/* (C)2025 */
package com.order_service.project.domain;

import com.order_service.project.domain.models.OrderSummary;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

interface OrderRepository extends JpaRepository<OrderEntity, Long> {

    @Query(value = "Select * from orders Where status = 'NEW'", nativeQuery = true)
    List<OrderEntity> getNewOrders();

    @Query(
            """
            select new com.order_service.project.domain.models.OrderSummary(o.orderNumber,o.status)
            from OrderEntity o
            where o.userName =:userName
            """)
    Optional<OrderSummary> findByUserName(String userName);
}
