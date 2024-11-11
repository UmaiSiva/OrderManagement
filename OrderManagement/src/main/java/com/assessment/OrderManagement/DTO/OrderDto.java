package com.assessment.OrderManagement.DTO;

import com.assessment.OrderManagement.Entity.Client;
import com.assessment.OrderManagement.Entity.Order;
import com.assessment.OrderManagement.Utility.OrderStatus;

import java.time.LocalDateTime;

public class OrderDto {
    public OrderDto(Order order) {
        this.id = order.getId();
        this.itemName = order.getItemName();
        this.orderReference = order.getOrderReference();
        this.quantity = order.getQuantity();
        this.shippingAddress = order.getShippingAddress();
        this.status = order.getStatus();
        this.timestamp = order.getTimestamp();
        this.clientName = order.getClient().getFirstName();
    }

    private Long id;
    private String itemName;
    private String orderReference;
    private Integer quantity;
    private String shippingAddress;
    private OrderStatus status;
    private LocalDateTime timestamp;
    private String clientName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getOrderReference() {
        return orderReference;
    }

    public void setOrderReference(String orderReference) {
        this.orderReference = orderReference;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }
}
