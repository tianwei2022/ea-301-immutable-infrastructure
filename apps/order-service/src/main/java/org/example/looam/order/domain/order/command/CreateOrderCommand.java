package org.example.looam.order.domain.order.command;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;

@Builder
public record CreateOrderCommand(
    @NotBlank String userId, @NotEmpty List<@Valid CreateOrderItemCommand> orderItems) {}
