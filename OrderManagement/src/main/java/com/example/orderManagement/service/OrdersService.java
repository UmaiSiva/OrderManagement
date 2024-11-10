package com.example.orderManagement.service;

import com.example.orderManagement.entity.Orders;
import com.example.orderManagement.entity.OrderStatus;
import com.example.orderManagement.repository.OrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import com.example.orderManagement.exception.OrderNotFoundException;



import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class OrdersService {

    @Autowired
    private OrdersRepository ordersRepository;

    public Orders placeOrder(Orders order) {
        order.setStatus(OrderStatus.NEW); // Use Enum for status
        order.setTimestamp(LocalDateTime.now());
        return ordersRepository.save(order);
    }

    public boolean cancelOrder(Long orderId) {
        Orders order = ordersRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with ID: " + orderId));

        if (OrderStatus.NEW.equals(order.getStatus())) {
            order.setStatus(OrderStatus.CANCELLED);
            ordersRepository.save(order);
            return true;
        }

        return false;
    }


    public List<Orders> getOrderHistory(Long clientId, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return ordersRepository.findByClientId(clientId, pageRequest).getContent();
    }




}
