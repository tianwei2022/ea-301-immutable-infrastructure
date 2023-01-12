package org.example.looam.book.inbound.rest;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.example.looam.book.appservice.BookAppService;
import org.example.looam.book.domain.book.Book;
import org.example.looam.book.domain.book.BookQuery;
import org.example.looam.book.domain.book.command.CreateBookCommand;
import org.example.looam.book.inbound.rest.dto.BookDTOMapper;
import org.example.looam.book.inbound.rest.dto.FindBookPageableRequest;
import org.example.looam.book.inbound.rest.dto.UpdateBookDescriptionRequest;
import org.example.looam.common.dto.CreatedResponse;
import org.example.looam.common.dto.PageQuery;
import org.example.looam.common.dto.PageResult;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
@Validated
public class BookController {
  private final BookAppService bookAppService;

  @PostMapping
  public CreatedResponse createBook(@RequestBody @Valid CreateBookCommand request) {
    return bookAppService.createBook(request);
  }

  @PostMapping("/batch")
  public List<Book> batchCreateBook(@RequestBody List<@Valid CreateBookCommand> request) {
    return bookAppService.batchCreateBook(request);
  }

  @PutMapping("/{id}/description")
  public void updateBookDescription(
      @PathVariable String id, @RequestBody @Valid UpdateBookDescriptionRequest request) {
    bookAppService.updateBookDescription(id, request.description());
  }

  @PutMapping("/{id}/tags")
  public void updateBookTags(@PathVariable String id, @RequestBody List<@NotBlank String> tags) {
    bookAppService.updateBookTags(id, tags);
  }

  @PostMapping("/{id}/show")
  public void showBook(@PathVariable String id) {
    bookAppService.showBook(id);
  }

  @DeleteMapping("/{id}")
  public void deleteBook(@PathVariable String id) {
    bookAppService.deleteBook(id);
  }

  @DeleteMapping("/batch")
  public void batchDeleteBook(@RequestBody List<@NotBlank String> ids) {
    bookAppService.batchDeleteBook(ids);
  }

  @GetMapping("/{id}")
  public Book findBook(@PathVariable String id) {
    return bookAppService.findById(id);
  }

  @GetMapping("/all")
  public List<Book> findBookAll(
      @RequestParam List<String> ids, @RequestParam(required = false) Boolean isVisible) {
    return bookAppService.findAll(BookQuery.builder().ids(ids).isVisible(isVisible).build());
  }

  @GetMapping
  public PageResult<Book> findBookPageable(@Valid FindBookPageableRequest request) {
    return bookAppService.findPage(
        BookDTOMapper.MAPPER.toQuery(request), new PageQuery(request.page(), request.size()));
  }
}
