package org.example.looam.book.domain.book;

import java.util.List;
import java.util.Optional;

import org.example.looam.common.dto.PageQuery;
import org.example.looam.common.dto.PageResult;

public interface BookRepository {
  Book save(Book book);

  Book saveSelf(Book book);

  List<Book> saveAll(List<Book> books);

  void delete(Book book);

  void deleteAll(List<Book> books);

  Optional<Book> findById(String id);

  List<Book> findAllByQuery(BookQuery query);

  PageResult<Book> findPageByQuery(BookQuery query, PageQuery pageQuery);
}
