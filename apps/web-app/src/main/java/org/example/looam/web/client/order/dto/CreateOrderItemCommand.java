package org.example.looam.web.client.order.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Builder;

@Builder
public record CreateOrderItemCommand(
    @NotBlank String bookId, @Positive long quantity, @PositiveOrZero BigDecimal dealPrice) {}
