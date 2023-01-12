package org.example.looam.web.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.ResultActions;

import org.example.looam.common.dto.PageResult;
import org.example.looam.web.client.book.BookServiceClient;
import org.example.looam.web.client.book.dto.BookDTO;
import org.example.looam.web.client.book.dto.BookTagDTO;
import org.example.looam.web.client.order.OrderServiceClient;
import org.example.looam.web.client.order.dto.CreateOrderCommand;
import org.example.looam.web.client.order.dto.CreateOrderItemCommand;
import org.example.looam.web.client.order.dto.FindOrderPageableParams;
import org.example.looam.web.client.order.dto.OrderDTO;
import org.example.looam.web.client.order.dto.OrderItemDTO;
import org.example.looam.web.client.order.dto.OrderStatus;
import org.example.looam.web.service.vo.Order;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class OrderControllerTest extends BaseControllerTest {
  @MockBean private BookServiceClient bookServiceClient;

  @MockBean private OrderServiceClient orderServiceClient;

  @Test
  void should_create_order() throws Exception {
    when(bookServiceClient.findBooksAll(List.of("book-1", "book-2")))
        .thenReturn(
            List.of(
                BookDTO.builder().id("book-1").visible(true).build(),
                BookDTO.builder().id("book-2").visible(true).build()));

    CreateOrderCommand command =
        CreateOrderCommand.builder()
            .userId("1")
            .orderItems(
                List.of(
                    new CreateOrderItemCommand("book-1", 2, BigDecimal.valueOf(20.00d)),
                    new CreateOrderItemCommand("book-2", 1, BigDecimal.valueOf(25.00d))))
            .build();

    perform(post("/orders"), command).andExpect(status().isOk());

    verify(orderServiceClient).createOrder(command);
  }

  @Test
  void should_fail_to_create_order_given_some_book_is_not_shown() throws Exception {
    when(bookServiceClient.findBooksAll(List.of("book-1", "book-2")))
        .thenReturn(
            List.of(
                BookDTO.builder().id("book-1").visible(true).build(),
                BookDTO.builder().id("book-2").visible(false).build()));

    CreateOrderCommand command =
        CreateOrderCommand.builder()
            .userId("1")
            .orderItems(
                List.of(
                    new CreateOrderItemCommand("book-1", 2, BigDecimal.valueOf(20.00d)),
                    new CreateOrderItemCommand("book-2", 1, BigDecimal.valueOf(25.00d))))
            .build();

    perform(post("/orders"), command).andExpect(status().isBadRequest());

    verify(orderServiceClient, times(0)).createOrder(command);
  }

  @Test
  void should_fail_to_create_order_given_book_does_not_exist() throws Exception {
    when(bookServiceClient.findBooksAll(List.of("book-1", "book-2")))
        .thenReturn(List.of(BookDTO.builder().id("book-2").build()));

    CreateOrderCommand command =
        CreateOrderCommand.builder()
            .userId("1")
            .orderItems(
                List.of(
                    new CreateOrderItemCommand("book-1", 2, BigDecimal.valueOf(20.00d)),
                    new CreateOrderItemCommand("book-2", 1, BigDecimal.valueOf(25.00d))))
            .build();

    perform(post("/orders"), command).andExpect(status().isBadRequest());

    verify(orderServiceClient, times(0)).createOrder(command);
  }

  @Test
  void should_complete_order() throws Exception {
    String orderId = "c0a80064-85a4-12fa-8185-a443166d0000";
    perform(post("/orders/{id}/complete", orderId)).andExpect(status().isOk());

    verify(orderServiceClient).completeOrder(orderId);
  }

  @Test
  void should_cancel_order() throws Exception {
    String orderId = "c0a80064-85a4-12fa-8185-a443166d0000";
    perform(post("/orders/{id}/cancel", orderId)).andExpect(status().isOk());

    verify(orderServiceClient).cancelOrder(orderId);
  }

  @Test
  void should_get_by_id() throws Exception {
    String orderId = "c0a80064-85a4-12fa-8185-a443166d0000";
    when(orderServiceClient.findOrder(orderId)).thenReturn(buildOrderWithId(orderId));
    when(bookServiceClient.findBooksAll(List.of("book-1", "book-2")))
        .thenReturn(List.of(buildWithId("book-1"), buildWithId("book-2")));

    ResultActions resultActions = perform(get("/orders/{id}", orderId)).andExpect(status().isOk());

    Order order = parseResponse(resultActions, Order.class);

    assertThat(order.getStatus()).isEqualTo(OrderStatus.COMPLETED);
    assertThat(order.getOrderItems()).hasSize(2);
    assertThat(order.getOrderItems().get(0).getBook()).isNotNull();
    assertThat(order.getOrderItems().get(0).getBook().title()).isEqualTo("西游记");
  }

  @Test
  void should_get_page_by_condition() throws Exception {
    OrderDTO orderDTO = buildOrderWithId("orderId");
    when(orderServiceClient.findOrderPageable(
            FindOrderPageableParams.builder().page(1).size(10).build()))
        .thenReturn(PageResult.<OrderDTO>builder().data(List.of(orderDTO)).total(1).build());
    when(bookServiceClient.findBooksAll(List.of("book-1", "book-2")))
        .thenReturn(List.of(buildWithId("book-1"), buildWithId("book-2")));

    ResultActions resultActions =
        perform(get("/orders").param("page", "1").param("size", "10")).andExpect(status().isOk());

    PageResult<Order> orderPageResult = parseResponse(resultActions, new TypeReference<>() {});

    assertThat(orderPageResult.total()).isEqualTo(1);
    assertThat(orderPageResult.data()).hasSize(1);
    assertThat(orderPageResult.data().get(0).getOrderItems()).hasSize(2);
    assertThat(orderPageResult.data().get(0).getOrderItems().get(0).getBook()).isNotNull();
    assertThat(orderPageResult.data().get(0).getOrderItems().get(0).getBook().title())
        .isEqualTo("西游记");
  }

  private static OrderDTO buildOrderWithId(String orderId) {
    return OrderDTO.builder()
        .id(orderId)
        .userId("1")
        .orderItems(
            List.of(
                new OrderItemDTO("book-1", 2, BigDecimal.valueOf(20.00d)),
                new OrderItemDTO("book-2", 1, BigDecimal.valueOf(25.00d))))
        .status(OrderStatus.COMPLETED)
        .completeAt(LocalDateTime.now())
        .build();
  }

  private static BookDTO buildWithId(String bookId) {
    return BookDTO.builder()
        .id(bookId)
        .author("吴承恩")
        .title("西游记")
        .price(BigDecimal.ONE)
        .asin("asin-1234")
        .visible(true)
        .publishedAt(LocalDate.now())
        .tags(List.of(new BookTagDTO("小说"), new BookTagDTO("古典文学"), new BookTagDTO("四大名著")))
        .build();
  }
}
