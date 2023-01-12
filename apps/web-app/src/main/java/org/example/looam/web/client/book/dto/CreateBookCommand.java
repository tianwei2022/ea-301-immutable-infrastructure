package org.example.looam.web.client.book.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;

@Builder
public record CreateBookCommand(
    @NotBlank String title,
    String description,
    @NotBlank String author,
    @NotBlank String asin,
    @NotNull @Positive BigDecimal price,
    @NotNull LocalDate publishedAt) {}
