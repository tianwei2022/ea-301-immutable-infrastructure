package org.example.looam.order.domain.order;

import java.util.List;

import lombok.Builder;

@Builder
public record OrderQuery(String orderNumber, String userId, List<OrderStatus> orderStatuses) {}
