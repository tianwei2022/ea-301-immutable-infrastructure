package org.example.looam.book.domain.book.command;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import jakarta.validation.Valid;
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
    @Valid List<@NotBlank String> tags,
    @NotNull LocalDate publishedAt) {}
