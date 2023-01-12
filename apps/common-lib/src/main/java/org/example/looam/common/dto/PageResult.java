package org.example.looam.common.dto;

import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.function.Function;
import lombok.Builder;

@Builder
public record PageResult<T>(List<T> data, long total) {
    public <U> PageResult<U> map(Function<? super T, ? extends U> mapper) {
        return PageResult.<U>builder()
                .total(total)
                .data(data.stream().map(mapper).collect(toList()))
                .build();
    }
}
