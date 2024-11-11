package com.assessment.OrderManagement.Service;

import com.assessment.OrderManagement.DTO.OrderDto;
import com.assessment.OrderManagement.DataAccess.OrderRepository;
import com.assessment.OrderManagement.Entity.Client;
import com.assessment.OrderManagement.Entity.Order;
import com.assessment.OrderManagement.Utility.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    public Order placeOrder(Order order) {
        order.setOrderReference(UUID.randomUUID().toString());
        order.setStatus(OrderStatus.NEW);
        return orderRepository.save(order);
    }

    public boolean cancelOrder(String orderReference, Client client) {
        Optional<Order> order = orderRepository.findByOrderReferenceAndClient(orderReference, client);
        if (order.isPresent() && order.get().getStatus() == OrderStatus.NEW) {
            order.get().setStatus(OrderStatus.CANCELLED);
            orderRepository.save(order.get());
            return true;
        }
        return false;
    }

    public Page<OrderDto> getOrderHistory(Client client, int page, int size) {
        Page<Order> orders = orderRepository.findAllByClient(client, PageRequest.of(page, size));
        return orders.map(this::MapOrderToDto);
    }

    @Scheduled(cron = "0 0 * * * *") // This  runs the task every hour at minute 0
    public void updateOrderStatusToDispatched() {
        List<Order> orders = orderRepository.findByStatus(OrderStatus.NEW);
        for (Order order : orders) {
            order.setStatus(OrderStatus.DISPATCHED);
            orderRepository.save(order);
            System.out.println("Order status updated to Dispatched : " + order.getOrderReference());
        }
        System.out.println("Order status updated to dispatched for all new orders.");
    }

    private OrderDto MapOrderToDto(Order order) {
        return  new OrderDto(order);
    }
}
