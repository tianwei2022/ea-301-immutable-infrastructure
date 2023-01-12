package org.example.looam.web.client.book.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import lombok.Builder;

@Builder
public record BookDTO(
    String id,
    String title,
    String description,
    String author,
    String asin,
    BigDecimal price,
    LocalDate publishedAt,
    List<BookTagDTO> tags,
    boolean visible,
    LocalDateTime visibleAt,
    LocalDateTime createdAt,
    LocalDateTime updatedAt) {}
