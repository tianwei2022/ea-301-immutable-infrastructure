package org.example.looam.book.inbound.rest.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.Positive;

public record FindBookPageableRequest(
    String searchKey,
    LocalDate publishedAt,
    Boolean isVisible,
    @Positive int page,
    @Positive int size) {}
