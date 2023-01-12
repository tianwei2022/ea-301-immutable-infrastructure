package org.example.looam.web.controller;

import java.util.List;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.example.looam.common.dto.CreatedResponse;
import org.example.looam.common.dto.PageResult;
import org.example.looam.web.client.order.dto.CreateOrderCommand;
import org.example.looam.web.client.order.dto.OrderStatus;
import org.example.looam.web.service.OrderService;
import org.example.looam.web.service.vo.Order;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
@Validated
public class OrderController {
  private final OrderService orderService;

  @PostMapping
  public CreatedResponse createOrder(@RequestBody @Valid CreateOrderCommand request) {
    return orderService.create(request);
  }

  @PostMapping("/{id}/complete")
  public void completeOrder(@PathVariable String id) {
    orderService.complete(id);
  }

  @PostMapping("/{id}/cancel")
  public void cancelOrder(@PathVariable String id) {
    orderService.cancel(id);
  }

  @GetMapping("/{id}")
  public Order findOrder(@PathVariable String id) {
    return orderService.findById(id);
  }

  @GetMapping
  public PageResult<Order> findOrderPageable(
      @RequestParam(required = false) String userId,
      @RequestParam(required = false) String orderNumber,
      @RequestParam(required = false) List<OrderStatus> orderStatuses,
      @RequestParam(defaultValue = "1") int page,
      @RequestParam(defaultValue = "10") int size) {
    return orderService.findPage(userId, orderNumber, orderStatuses, page, size);
  }
}
