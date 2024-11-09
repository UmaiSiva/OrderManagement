package com.example.ordermanagement.service;

import com.example.ordermanagement.entity.Client;
import com.example.ordermanagement.entity.Order;
import com.example.ordermanagement.repository.ClientRepository;
import com.example.ordermanagement.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ClientRepository clientRepository;

/**
 * Places a new order for the specified client.
 * @param order - Order object with details of the order.
 * @return The saved Order object after placement.
 */

public Order placeOrder(Order order) {
    Optional<Client> client = clientRepository.findById(order.getClient().getId());
    if (client.isPresent()) {
        order.setClient(client.get());
        order.setStatus("NEW");
        order.setTimestamp(LocalDateTime.now());
        return orderRepository.save(order);
    } else {
        throw new RuntimeException("Client not found");
    }
}

/**
 * Cancels an order by its ID, if its status is "NEW".
 * @param orderId - ID of the order to cancel.
 * @return A message indicating success or failure of the cancellation.
 */

public String cancelOrder(Long orderId) {
    Optional<Order> order = orderRepository.findById(orderId);
    if (order.isPresent() && "NEW".equals(order.get().getStatus())) {
        order.get().setStatus("CANCELLED");
        orderRepository.save(order.get());
        return "Order cancelled successfully!";
    } else {
        return "Order cannot be cancelled (either not found or already dispatched).";
    }
}

    /**
     * Retrieves the order history for a specific client.
     * @param clientId - ID of the client whose orders are being retrieved.
     * @return List of orders for the specified client.
     */
    public List<Order> getOrderHistory(Long clientId) {
        return orderRepository.findByClientId(clientId);
    }

    /**
     * Scheduled task to dispatch orders that are still in "NEW" status.
     * This task runs every hour to check and update eligible orders.
     */
    @Scheduled(cron = "0 0 * * * *") // Runs every hour
    public void dispatchOrders() {
        List<Order> newOrders = orderRepository.findByStatus("NEW");
        for (Order order : newOrders) {
            order.setStatus("DISPATCHED");
            orderRepository.save(order);
        }
        System.out.println("Dispatched all NEW orders at: " + LocalDateTime.now());
    }
}