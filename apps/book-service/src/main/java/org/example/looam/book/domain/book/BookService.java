package org.example.looam.book.domain.book;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import org.example.looam.book.domain.book.command.CreateBookCommand;

@Service
@RequiredArgsConstructor
public class BookService {
  private final BookRepository bookRepository;

  public Book create(CreateBookCommand command) {
    Book book = Book.create(command);
    return bookRepository.save(book);
  }

  public List<Book> batchCreate(List<CreateBookCommand> commands) {
    List<Book> books = commands.stream().map(Book::create).toList();
    return bookRepository.saveAll(books);
  }

  public void updateTags(Book book, List<String> tags) {
    book.updateTags(tags);
    bookRepository.save(book);
  }

  public void updateDescription(Book book, String description) {
    book.updateDescription(description);
    bookRepository.saveSelf(book);
  }

  public void show(Book book) {
    book.show();
    bookRepository.saveSelf(book);
  }

  public void delete(Book book) {
    bookRepository.delete(book);
  }

  public void deleteAll(List<Book> books) {
    bookRepository.deleteAll(books);
  }
}
