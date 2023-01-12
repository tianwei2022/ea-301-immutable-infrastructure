package org.example.looam.book.inbound.rest.dto;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import org.example.looam.book.domain.book.BookQuery;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BookDTOMapper {
  BookDTOMapper MAPPER = Mappers.getMapper(BookDTOMapper.class);

  BookQuery toQuery(FindBookPageableRequest request);
}
