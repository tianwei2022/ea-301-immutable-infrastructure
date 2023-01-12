package org.example.looam.order.inbound.rest.dto;

import java.util.List;

import jakarta.validation.constraints.Positive;

import org.example.looam.order.domain.order.OrderStatus;

public record FindOrderPageableRequest(
    String orderNumber,
    String userId,
    List<OrderStatus> orderStatuses,
    @Positive int page,
    @Positive int size) {}
