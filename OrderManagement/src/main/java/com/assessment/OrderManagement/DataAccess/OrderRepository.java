package com.assessment.OrderManagement.DataAccess;

import com.assessment.OrderManagement.Entity.Client;
import com.assessment.OrderManagement.Entity.Order;
import com.assessment.OrderManagement.Utility.OrderStatus;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findByOrderReferenceAndClient(String orderReference, Client client);

    Page<Order> findAllByClient(Client client, Pageable pageable);

    List<Order> findByStatus(OrderStatus status);
}
