package org.example.looam.web.service;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import org.example.looam.common.dto.CreatedResponse;
import org.example.looam.common.dto.PageResult;
import org.example.looam.web.client.book.BookServiceClient;
import org.example.looam.web.client.book.dto.BookDTO;
import org.example.looam.web.client.book.dto.CreateBookCommand;
import org.example.looam.web.client.book.dto.FindBookPageableParams;

@Service
@RequiredArgsConstructor
public class BookService {
  private final BookServiceClient bookServiceClient;

  public CreatedResponse createBook(CreateBookCommand command) {
    return bookServiceClient.createBook(command);
  }

  public void updateBookTags(String id, List<String> tags) {
    bookServiceClient.updateBookTags(id, tags);
  }

  public void showBook(String id) {
    bookServiceClient.showBook(id);
  }

  public BookDTO findById(String id) {
    return bookServiceClient.findBook(id);
  }

  public PageResult<BookDTO> findPage(String searchKey, int page, int size) {
    return bookServiceClient.findBookPageable(
        new FindBookPageableParams(searchKey, true, page, size));
  }
}
