package org.example.looam.web.service.vo;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItem {
  @JsonIgnore String bookId;
  Book book;
  long quantity;
  BigDecimal dealPrice;
}
