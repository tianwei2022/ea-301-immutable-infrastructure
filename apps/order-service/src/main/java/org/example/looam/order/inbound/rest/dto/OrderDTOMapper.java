package org.example.looam.order.inbound.rest.dto;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import org.example.looam.order.domain.order.OrderQuery;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrderDTOMapper {
  OrderDTOMapper MAPPER = Mappers.getMapper(OrderDTOMapper.class);

  OrderQuery toQuery(FindOrderPageableRequest request);
}
