package com.example.ordermanagement.controller;

import com.example.ordermanagement.entity.Order;
import com.example.ordermanagement.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * Endpoint for placing a new order.
     * @param order - Order object containing order details.
     * @return The placed Order object.
     */
    @PostMapping
    public Order placeOrder(@RequestBody Order order) {
        return orderService.placeOrder(order);
    }

    /**
     * Endpoint for canceling an order by its ID.
     * @param orderId - ID of the order to cancel.
     * @return A message indicating success or failure.
     */
    @DeleteMapping("/{orderId}")
    public String cancelOrder(@PathVariable Long orderId) {
        return orderService.cancelOrder(orderId);
    }

    /**
     * Endpoint for retrieving order history for a specific client.
     * @param clientId - ID of the client whose order history is requested.
     * @return List of orders for the specified client.
     */
    @GetMapping("/history/{clientId}")
    public List<Order> getOrderHistory(@PathVariable Long clientId) {
        return orderService.getOrderHistory(clientId);
    }
}
