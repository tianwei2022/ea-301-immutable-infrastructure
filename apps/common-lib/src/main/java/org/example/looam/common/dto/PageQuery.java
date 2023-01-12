package org.example.looam.common.dto;

import lombok.Builder;

@Builder
public record PageQuery(int page, int size) {
}
