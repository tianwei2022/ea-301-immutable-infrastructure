package org.example.looam.order.inbound.rest;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.example.looam.order.appservice.OrderAppService;
import org.example.looam.order.domain.order.command.CreateOrderCommand;
import org.example.looam.order.domain.order.command.CreateOrderItemCommand;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class OrderControllerTest extends BaseControllerTest {
  @MockBean private OrderAppService orderAppService;

  @Test
  void createOrder_should_fail_given_no_user_id() throws Exception {
    CreateOrderCommand command =
        CreateOrderCommand.builder()
            .orderItems(
                List.of(
                    new CreateOrderItemCommand("book-1", 2, BigDecimal.valueOf(20.00d)),
                    new CreateOrderItemCommand("book-2", 1, BigDecimal.valueOf(25.00d))))
            .build();

    perform(post("/orders"), command).andExpect(status().isBadRequest());
  }

  @Test
  void createOrder_should_fail_given_no_orderItems() throws Exception {
    CreateOrderCommand command =
        CreateOrderCommand.builder().userId("1").orderItems(List.of()).build();

    perform(post("/orders"), command).andExpect(status().isBadRequest());
  }

  @Test
  void createOrder_should_fail_given_any_order_item_is_invalid() throws Exception {
    CreateOrderCommand command =
        CreateOrderCommand.builder()
            .userId("1")
            .orderItems(
                List.of(
                    new CreateOrderItemCommand("", 2, BigDecimal.valueOf(20.00d)),
                    new CreateOrderItemCommand("book-2", 1, BigDecimal.valueOf(25.00d))))
            .build();

    perform(post("/orders"), command).andExpect(status().isBadRequest());
  }

  @Test
  void find_pageable_should_fail_given_no_page_param() throws Exception {
    perform(get("/orders").param("size", "10")).andExpect(status().isBadRequest());
  }
}
