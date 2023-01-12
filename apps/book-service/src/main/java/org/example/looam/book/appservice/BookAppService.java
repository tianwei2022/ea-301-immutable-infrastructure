package org.example.looam.book.appservice;

import java.util.List;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import org.example.looam.book.domain.book.Book;
import org.example.looam.book.domain.book.BookQuery;
import org.example.looam.book.domain.book.BookRepository;
import org.example.looam.book.domain.book.BookService;
import org.example.looam.book.domain.book.command.CreateBookCommand;
import org.example.looam.common.dto.CreatedResponse;
import org.example.looam.common.dto.PageQuery;
import org.example.looam.common.dto.PageResult;
import org.example.looam.common.exception.DataNotFoundException;

@Service
@RequiredArgsConstructor
public class BookAppService {
  private final BookService bookService;
  private final BookRepository bookRepository;

  @Transactional
  public CreatedResponse createBook(CreateBookCommand request) {
    return new CreatedResponse(bookService.create(request).getId());
  }

  @Transactional
  public List<Book> batchCreateBook(List<CreateBookCommand> request) {
    return bookService.batchCreate(request);
  }

  @Transactional
  public void updateBookDescription(String id, String description) {
    Book book = bookRepository.findById(id).orElseThrow(DataNotFoundException::new);
    bookService.updateDescription(book, description);
  }

  @Transactional
  public void updateBookTags(String id, List<String> tags) {
    Book book = bookRepository.findById(id).orElseThrow(DataNotFoundException::new);
    bookService.updateTags(book, tags);
  }

  @Transactional
  public void showBook(String id) {
    Book book = bookRepository.findById(id).orElseThrow(DataNotFoundException::new);
    bookService.show(book);
  }

  @Transactional
  public void deleteBook(String id) {
    Book book = bookRepository.findById(id).orElseThrow(DataNotFoundException::new);
    bookService.delete(book);
  }

  @Transactional
  public void batchDeleteBook(List<String> ids) {
    List<Book> books = bookRepository.findAllByQuery(BookQuery.builder().ids(ids).build());
    bookService.deleteAll(books);
  }

  public Book findById(String id) {
    return bookRepository.findById(id).orElseThrow(DataNotFoundException::new);
  }

  public List<Book> findAll(BookQuery query) {
    return bookRepository.findAllByQuery(query);
  }

  public PageResult<Book> findPage(BookQuery query, PageQuery pageQuery) {
    return bookRepository.findPageByQuery(query, pageQuery);
  }
}
