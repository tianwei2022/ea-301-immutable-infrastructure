package org.example.looam.web.client.order.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import lombok.Builder;

@Builder
public record OrderDTO(
    String id,
    String userId,
    String orderNumber,
    List<OrderItemDTO> orderItems,
    BigDecimal totalPrice,
    OrderStatus status,
    LocalDateTime completeAt,
    LocalDateTime cancelAt,
    LocalDateTime createdAt,
    LocalDateTime updatedAt) {}
