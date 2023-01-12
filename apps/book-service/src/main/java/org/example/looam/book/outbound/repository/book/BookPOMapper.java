package org.example.looam.book.outbound.repository.book;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import org.example.looam.book.domain.book.Book;
import org.example.looam.book.domain.book.BookTag;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BookPOMapper {

  BookPOMapper MAPPER = Mappers.getMapper(BookPOMapper.class);

  Book toModel(BookPO bookPO);

  BookPO toPO(Book book);

  BookTag toModel(BookTagPO bookTagPO);

  BookTagPO toPO(BookTag bookTag);
}
