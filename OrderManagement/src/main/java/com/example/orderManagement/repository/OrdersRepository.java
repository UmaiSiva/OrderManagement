package com.example.orderManagement.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import com.example.orderManagement.entity.Orders;
import com.example.orderManagement.entity.OrderStatus;


public interface OrdersRepository extends
        JpaRepository<Orders, Long> {
    Page<Orders> findByClientId(Long clientId, Pageable pageable);
    List<Orders> findByStatus(OrderStatus status);
}
