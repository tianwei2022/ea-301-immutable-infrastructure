package org.example.looam.order.domain.order;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import org.example.looam.order.domain.order.command.CreateOrderCommand;

@Service
@RequiredArgsConstructor
public class OrderService {
  private final OrderRepository orderRepository;

  public Order create(CreateOrderCommand command) {
    Order order = Order.create(command);
    return orderRepository.save(order);
  }

  public void complete(Order order) {
    order.complete();
    orderRepository.saveSelf(order);
  }

  public void cancel(Order order) {
    order.cancel();
    orderRepository.saveSelf(order);
  }
}
