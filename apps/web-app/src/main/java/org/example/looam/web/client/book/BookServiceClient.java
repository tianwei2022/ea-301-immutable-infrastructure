package org.example.looam.web.client.book;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import org.example.looam.common.dto.CreatedResponse;
import org.example.looam.common.dto.PageResult;
import org.example.looam.web.client.book.dto.BookDTO;
import org.example.looam.web.client.book.dto.CreateBookCommand;
import org.example.looam.web.client.book.dto.FindBookPageableParams;

@FeignClient(
    name = "book-service",
    url = "${client.book-service.url:book-service}",
    primary = false)
public interface BookServiceClient {
  @PostMapping("/books")
  CreatedResponse createBook(@RequestBody @Valid CreateBookCommand request);

  @PutMapping("/books/{id}/tags")
  void updateBookTags(@PathVariable String id, @RequestBody List<@NotBlank String> tags);

  @PostMapping("/books/{id}/show")
  void showBook(@PathVariable String id);

  @GetMapping("/books/{id}")
  BookDTO findBook(@PathVariable String id);

  @GetMapping("/books")
  PageResult<BookDTO> findBookPageable(@SpringQueryMap FindBookPageableParams request);

  @GetMapping("/books/all")
  public List<BookDTO> findBooksAll(@RequestParam List<String> ids);
}
