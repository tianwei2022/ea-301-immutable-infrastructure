package org.example.looam.book.domain.book;

import java.time.LocalDate;
import java.util.List;

import lombok.Builder;

@Builder
public record BookQuery(
    String id, List<String> ids, String searchKey, LocalDate publishedAt, Boolean isVisible) {}
