package org.example.looam.web.client.order.dto;

import java.util.List;

import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FindOrderPageableParams {
  String orderNumber;
  String userId;
  List<OrderStatus> orderStatuses;
  @Positive int page;
  @Positive int size;
}
