package org.example.looam.web.service.vo;

import java.util.Map;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import org.example.looam.web.client.book.dto.BookDTO;
import org.example.looam.web.client.order.dto.OrderDTO;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrderMapper {
  OrderMapper MAPPER = Mappers.getMapper(OrderMapper.class);

  Order toModel(OrderDTO orderDTO);

  Book toModel(BookDTO bookDTO);

  default Order toOrder(OrderDTO orderDTO, Map<String, BookDTO> bookMap) {
    Order order = MAPPER.toModel(orderDTO);
    order.getOrderItems().forEach(item -> item.setBook(MAPPER.toModel(bookMap.get(item.bookId))));
    return order;
  }
  ;
}
