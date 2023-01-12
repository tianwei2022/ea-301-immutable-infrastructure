package org.example.looam.order.domain.order;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import org.example.looam.order.domain.order.command.CreateOrderCommand;
import org.example.looam.order.domain.order.command.CreateOrderItemCommand;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrderMapper {
  OrderMapper MAPPER = Mappers.getMapper(OrderMapper.class);

  Order toModel(CreateOrderCommand command);

  OrderItem toModel(CreateOrderItemCommand command);
}
