package org.example.looam.web.client.order;

import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.example.looam.common.dto.CreatedResponse;
import org.example.looam.common.dto.PageResult;
import org.example.looam.web.client.order.dto.CreateOrderCommand;
import org.example.looam.web.client.order.dto.FindOrderPageableParams;
import org.example.looam.web.client.order.dto.OrderDTO;

@FeignClient(
    name = "order-service",
    url = "${client.order-service.url:order-service}",
    primary = false)
public interface OrderServiceClient {
  @PostMapping("/orders")
  CreatedResponse createOrder(@RequestBody @Valid CreateOrderCommand request);

  @PostMapping("/orders/{id}/complete")
  void completeOrder(@PathVariable String id);

  @PostMapping("/orders/{id}/cancel")
  void cancelOrder(@PathVariable String id);

  @GetMapping("/orders/{id}")
  OrderDTO findOrder(@PathVariable String id);

  @GetMapping("/orders")
  PageResult<OrderDTO> findOrderPageable(@SpringQueryMap FindOrderPageableParams request);
}
