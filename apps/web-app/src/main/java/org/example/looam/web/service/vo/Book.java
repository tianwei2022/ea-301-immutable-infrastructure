package org.example.looam.web.service.vo;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Builder;

@Builder
public record Book(
    String id,
    String title,
    String description,
    String author,
    String asin,
    BigDecimal price,
    LocalDate publishedAt) {}
