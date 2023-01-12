package org.example.looam.order.domain.order;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import org.example.looam.common.exception.AppException;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class OrderTest {

  @Test
  void complete_should_success_given_status_is_CREATED() {
    Order order = buildOrder(OrderStatus.CREATED);
    assertThatNoException().isThrownBy(order::complete);
  }

  @ParameterizedTest
  @EnumSource(value = OrderStatus.class, names = "CREATED", mode = EnumSource.Mode.EXCLUDE)
  void complete_should_fail_given_status_is_not_CREATED(OrderStatus status) {
    Order order = buildOrder(status);
    assertThatThrownBy(order::complete).isInstanceOf(AppException.class);
  }

  @Test
  void cancel_should_success_given_status_is_CREATED() {
    Order order = buildOrder(OrderStatus.CREATED);
    assertThatNoException().isThrownBy(order::cancel);
  }

  @ParameterizedTest
  @EnumSource(value = OrderStatus.class, names = "CREATED", mode = EnumSource.Mode.EXCLUDE)
  void cancel_should_fail_given_status_is_not_CREATED(OrderStatus status) {
    Order order = buildOrder(status);
    assertThatThrownBy(order::cancel).isInstanceOf(AppException.class);
  }

  private static Order buildOrder(OrderStatus status) {
    return Order.builder()
        .id("orderId")
        .userId("1")
        .orderItems(
            List.of(
                new OrderItem("orderId", "book-1", 2, BigDecimal.valueOf(20.00d)),
                new OrderItem("orderId", "book-2", 1, BigDecimal.valueOf(25.00d))))
        .status(status)
        .build();
  }
}
