package com.example.orderManagement.dto;

public class OrderResponseDTO {
    private Long orderId;
    private String status;

    public OrderResponseDTO(Long orderId, String status) {
        this.orderId = orderId;
        this.status = status;
    }

    // Getters
    public Long getOrderId() {
        return orderId;
    }

    public String getStatus() {
        return status;
    }

    // Setters
    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
