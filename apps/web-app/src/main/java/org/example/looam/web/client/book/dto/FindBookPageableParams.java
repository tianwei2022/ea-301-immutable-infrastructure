package org.example.looam.web.client.book.dto;

import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FindBookPageableParams {
  String searchKey;
  Boolean isVisible;
  @Positive int page;
  @Positive int size;
}
