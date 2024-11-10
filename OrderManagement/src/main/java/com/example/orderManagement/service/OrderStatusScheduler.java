package com.example.orderManagement.service;

import com.example.orderManagement.entity.Orders;
import com.example.orderManagement.entity.OrderStatus;
import com.example.orderManagement.repository.OrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderStatusScheduler {

    @Autowired
    private OrdersRepository ordersRepository;

    @Scheduled(cron = "0 0 * * * ?") // Runs hourly
    public void updateOrderStatus() {
        List<Orders> newOrders = ordersRepository.findByStatus(OrderStatus.NEW);
        for (Orders order : newOrders) {
            order.setStatus(OrderStatus.DISPATCHED);
            ordersRepository.save(order);
        }
    }
}
