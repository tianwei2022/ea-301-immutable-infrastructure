package org.example.looam.web.service.vo;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.example.looam.web.client.order.dto.OrderStatus;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {
  String id;
  String userId;
  String orderNumber;
  List<OrderItem> orderItems;
  BigDecimal totalPrice;
  OrderStatus status;
  LocalDateTime completeAt;
  LocalDateTime cancelAt;
  LocalDateTime createdAt;
  LocalDateTime updatedAt;
}
