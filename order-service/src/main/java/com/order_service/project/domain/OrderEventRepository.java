package com.order_service.project.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import java.math.BigInteger;

public interface OrderEventRepository extends JpaRepository<OrderEventEntity, Long> {
}
