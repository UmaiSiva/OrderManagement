package com.example.orderManagement.controller;

import com.example.orderManagement.dto.OrderResponseDTO;
import com.example.orderManagement.entity.Orders;
import com.example.orderManagement.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrdersController {

    @Autowired
    private OrdersService ordersService;

    // Place a new order
    @PostMapping("/place")
    public OrderResponseDTO placeOrder(@RequestBody Orders order) {
        Orders placedOrder = ordersService.placeOrder(order);
        return new OrderResponseDTO(placedOrder.getId(), placedOrder.getStatus().toString());
    }

    // Cancel an order by ID
    @PutMapping("/cancel/{orderId}")
    public String cancelOrder(@PathVariable Long orderId) {
        boolean canceled = ordersService.cancelOrder(orderId);
        return canceled ? "Order canceled successfully." : "Order cancellation failed or order not in NEW status.";
    }

    // Get paginated order history for a client
    @GetMapping("/history/{clientId}")
    public List<Orders> getOrderHistory(
            @PathVariable Long clientId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ordersService.getOrderHistory(clientId, page, size);
    }
}
