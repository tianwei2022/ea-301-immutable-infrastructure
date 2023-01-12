package org.example.looam.web.client.order.dto;

import java.math.BigDecimal;

import lombok.Builder;

@Builder
public record OrderItemDTO(String bookId, long quantity, BigDecimal dealPrice) {}
