package org.example.looam.book.inbound.rest.dto;

import jakarta.validation.constraints.NotBlank;

public record UpdateBookDescriptionRequest(@NotBlank String description) {}
