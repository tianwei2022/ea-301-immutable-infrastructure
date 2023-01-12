package org.example.looam.web.service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import org.example.looam.common.dto.CreatedResponse;
import org.example.looam.common.dto.PageResult;
import org.example.looam.common.exception.AppException;
import org.example.looam.common.exception.ErrorCode;
import org.example.looam.web.client.book.BookServiceClient;
import org.example.looam.web.client.book.dto.BookDTO;
import org.example.looam.web.client.order.OrderServiceClient;
import org.example.looam.web.client.order.dto.CreateOrderCommand;
import org.example.looam.web.client.order.dto.CreateOrderItemCommand;
import org.example.looam.web.client.order.dto.FindOrderPageableParams;
import org.example.looam.web.client.order.dto.OrderDTO;
import org.example.looam.web.client.order.dto.OrderItemDTO;
import org.example.looam.web.client.order.dto.OrderStatus;
import org.example.looam.web.service.vo.Order;
import org.example.looam.web.service.vo.OrderMapper;

@Service
@RequiredArgsConstructor
public class OrderService {
  private final OrderServiceClient orderServiceClient;
  private final BookServiceClient bookServiceClient;

  public CreatedResponse create(CreateOrderCommand request) {
    List<String> bookIds =
        request.orderItems().stream().map(CreateOrderItemCommand::bookId).toList();
    Map<String, BookDTO> bookMap =
        bookServiceClient.findBooksAll(bookIds).stream()
            .collect(Collectors.toMap(BookDTO::id, Function.identity()));
    bookIds.stream()
        .filter(id -> !bookMap.containsKey(id) || !bookMap.get(id).visible())
        .findAny()
        .ifPresent(
            id -> {
              throw new AppException(new ErrorCode("book_not_found"), id);
            });
    return orderServiceClient.createOrder(request);
  }

  public void complete(String id) {
    orderServiceClient.completeOrder(id);
  }

  public void cancel(String id) {
    orderServiceClient.cancelOrder(id);
  }

  public Order findById(String id) {
    OrderDTO orderDTO = orderServiceClient.findOrder(id);
    Map<String, BookDTO> bookMap =
        bookServiceClient
            .findBooksAll(orderDTO.orderItems().stream().map(OrderItemDTO::bookId).toList())
            .stream()
            .collect(Collectors.toMap(BookDTO::id, Function.identity()));
    return OrderMapper.MAPPER.toOrder(orderDTO, bookMap);
  }

  public PageResult<Order> findPage(
      String userId, String orderNumber, List<OrderStatus> orderStatuses, int page, int size) {
    PageResult<OrderDTO> orderDTOPageResult =
        orderServiceClient.findOrderPageable(
            new FindOrderPageableParams(orderNumber, userId, orderStatuses, page, size));
    Map<String, BookDTO> bookMap =
        bookServiceClient
            .findBooksAll(
                orderDTOPageResult.data().stream()
                    .map(OrderDTO::orderItems)
                    .flatMap(List::stream)
                    .map(OrderItemDTO::bookId)
                    .toList())
            .stream()
            .collect(Collectors.toMap(BookDTO::id, Function.identity()));
    return orderDTOPageResult.map(dto -> OrderMapper.MAPPER.toOrder(dto, bookMap));
  }
}
