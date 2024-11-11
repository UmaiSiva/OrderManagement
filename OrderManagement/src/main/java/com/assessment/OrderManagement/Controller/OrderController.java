package com.assessment.OrderManagement.Controller;

import com.assessment.OrderManagement.DTO.OrderDto;
import com.assessment.OrderManagement.Entity.*;
import com.assessment.OrderManagement.Service.*;
import com.assessment.OrderManagement.Utility.JwtUtil;
import com.assessment.OrderManagement.Utility.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private ClientService clientService;
    @Autowired
    private JwtUtil jwtUtil;

    public OrderController(OrderService orderService, JwtUtil jwtUtil, ClientService clientService) {
        this.orderService = orderService;
        this.jwtUtil = jwtUtil;
        this.clientService = clientService;
    }

    @PostMapping
    public ResponseEntity<String> placeOrder(@RequestBody Order order, @RequestHeader("Authorization") String authorizationHeader) {
        String token = authorizationHeader.replace("Bearer ", "");

        String username = jwtUtil.extractUsername(token);

        if (jwtUtil.isTokenExpired(token)) {
            throw new UnauthorizedException("Token has expired");
        }

        Optional<Client> client = clientService.findByEmail(username);
        if (client.isEmpty()) {
            throw new UnauthorizedException("Client not found");
        }

        order.setClient(client.get());
        order.setTimestamp(LocalDateTime.now());

        Order placedOrder = orderService.placeOrder(order);
        return ResponseEntity.ok("Order placed with reference: " + placedOrder.getOrderReference());
    }

    @PutMapping("/cancel")
    public ResponseEntity<String> cancelOrder(@RequestParam String orderReference, @RequestHeader("Authorization") String authorizationHeader) {
        String token = authorizationHeader.replace("Bearer ", "");

        String username = jwtUtil.extractUsername(token);

        if (jwtUtil.isTokenExpired(token)) {
            throw new UnauthorizedException("Token has expired");
        }

        Optional<Client> client = clientService.findByEmail(username);
        if (client.isEmpty()) {
            throw new UnauthorizedException("Client not found");
        }

        boolean canceled = orderService.cancelOrder(orderReference, client.get());
        if (canceled) {
            return ResponseEntity.ok("Order cancelled successfully");
        } else {
            return ResponseEntity.badRequest().body("Order cancellation failed. Order may not exist or is already dispatched.");
        }
    }

    @GetMapping
    public ResponseEntity<List<OrderDto>> getOrderHistory(@RequestParam int page, @RequestParam int size,@RequestHeader("Authorization") String authorizationHeader) {
        String token = authorizationHeader.replace("Bearer ", "");

        String username = jwtUtil.extractUsername(token);

        if (jwtUtil.isTokenExpired(token)) {
            throw new UnauthorizedException("Token has expired");
        }

        Optional<Client> client = clientService.findByEmail(username);
        if (client.isEmpty()) {
            throw new UnauthorizedException("Client not found");
        }

        List<OrderDto> orders = orderService.getOrderHistory(client.get(), page, size).getContent();
        return ResponseEntity.ok(orders);
    }
}
