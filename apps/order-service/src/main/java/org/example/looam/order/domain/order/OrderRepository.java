package org.example.looam.order.domain.order;

import java.util.Optional;

import org.example.looam.common.dto.PageQuery;
import org.example.looam.common.dto.PageResult;

public interface OrderRepository {
  Order save(Order order);

  Order saveSelf(Order order);

  Optional<Order> findById(String id);

  PageResult<Order> findPageByQuery(OrderQuery query, PageQuery pageQuery);
}
