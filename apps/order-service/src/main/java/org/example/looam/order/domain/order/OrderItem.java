package org.example.looam.order.domain.order;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderItem {
  private String orderId;
  private String bookId;
  private long quantity;
  private BigDecimal dealPrice;
}
