package org.example.looam.order.outbound.repository.order;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import org.example.looam.order.domain.order.Order;
import org.example.looam.order.domain.order.OrderItem;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrderPOMapper {

  OrderPOMapper MAPPER = Mappers.getMapper(OrderPOMapper.class);

  Order toModel(OrderPO orderPO);

  OrderPO toPO(Order order);

  OrderItem toModel(OrderItemPO orderItemPO);

  OrderItemPO toPO(OrderItem bookTag);
}
