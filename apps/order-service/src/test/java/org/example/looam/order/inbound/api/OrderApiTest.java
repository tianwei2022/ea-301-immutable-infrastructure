package org.example.looam.order.inbound.api;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.ResultActions;

import org.example.looam.common.dto.CreatedResponse;
import org.example.looam.common.dto.PageResult;
import org.example.looam.order.domain.order.Order;
import org.example.looam.order.domain.order.OrderStatus;
import org.example.looam.order.domain.order.command.CreateOrderCommand;
import org.example.looam.order.domain.order.command.CreateOrderItemCommand;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class OrderApiTest extends BaseApiTest {
  @Test
  void should_create_order() throws Exception {
    CreateOrderCommand command =
        CreateOrderCommand.builder()
            .userId("1")
            .orderItems(
                List.of(
                    new CreateOrderItemCommand("book-1", 2, BigDecimal.valueOf(20.00d)),
                    new CreateOrderItemCommand("book-2", 1, BigDecimal.valueOf(25.00d))))
            .build();

    ResultActions resultActions = perform(post("/orders"), command).andExpect(status().isOk());

    CreatedResponse createdResponse = parseResponse(resultActions, CreatedResponse.class);

    String id = createdResponse.id();

    assertThat(id).isNotNull();

    ResultActions findByIdResultActions =
        perform(get("/orders/{id}", id)).andExpect(status().isOk());

    Order order = parseResponse(findByIdResultActions, Order.class);

    assertThat(order.getOrderNumber()).hasSize(17);
    assertThat(order.getStatus()).isEqualTo(OrderStatus.CREATED);
    assertThat(order.getTotalPrice()).isEqualByComparingTo("65");
    assertThat(order.getCompleteAt()).isNull();
    assertThat(order.getCancelAt()).isNull();
    assertThat(order.getOrderItems()).hasSize(2);
  }

  @Test
  @Sql("/db/order/several-orders.sql")
  void should_complete_order() throws Exception {
    String orderId = "c0a80064-85a4-12fa-8185-a443166d0000";
    perform(post("/orders/{id}/complete", orderId)).andExpect(status().isOk());

    ResultActions resultActions = perform(get("/orders/{id}", orderId)).andExpect(status().isOk());
    Order order = parseResponse(resultActions, Order.class);
    assertThat(order.getStatus()).isEqualTo(OrderStatus.COMPLETED);
    assertThat(order.getCompleteAt()).isNotNull();
    assertThat(order.getCancelAt()).isNull();
  }

  @Test
  @Sql("/db/order/several-orders.sql")
  void should_cancel_order() throws Exception {
    String orderId = "c0a80064-85a4-12fa-8185-a443166d0000";
    perform(post("/orders/{id}/cancel", orderId)).andExpect(status().isOk());

    ResultActions resultActions = perform(get("/orders/{id}", orderId)).andExpect(status().isOk());
    Order order = parseResponse(resultActions, Order.class);
    assertThat(order.getStatus()).isEqualTo(OrderStatus.CANCELLED);
    assertThat(order.getCompleteAt()).isNull();
    assertThat(order.getCancelAt()).isNotNull();
  }

  @Test
  @Sql("/db/order/several-orders.sql")
  void should_get_page_by_condition() throws Exception {
    ResultActions resultActions =
        perform(
                get("/orders")
                    .param("page", "1")
                    .param("size", "10")
                    .param("orderStatuses", OrderStatus.CREATED.toString())
                    .param("orderStatuses", OrderStatus.COMPLETED.toString())
                    .param("userId", "2"))
            .andExpect(status().isOk());

    PageResult<Order> orderPageResult = parseResponse(resultActions, new TypeReference<>() {});

    assertThat(orderPageResult.total()).isEqualTo(3);
    assertThat(orderPageResult.data()).hasSize(3).allMatch(order -> "2".equals(order.getUserId()));
    assertThat(orderPageResult.data())
        .extracting("status")
        .contains(OrderStatus.CREATED, OrderStatus.COMPLETED);
  }
}
