package org.example.looam.book.inbound.api;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.ResultActions;

import org.example.looam.book.domain.book.Book;
import org.example.looam.book.domain.book.BookTag;
import org.example.looam.book.domain.book.command.CreateBookCommand;
import org.example.looam.book.inbound.rest.dto.UpdateBookDescriptionRequest;
import org.example.looam.common.dto.CreatedResponse;
import org.example.looam.common.dto.PageResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class BookApiTest extends BaseApiTest {
  @Test
  void should_create_book() throws Exception {
    CreateBookCommand command =
        CreateBookCommand.builder()
            .title("西游记")
            .author("吴承恩")
            .price(BigDecimal.ONE)
            .asin("asin-1234")
            .tags(List.of("tag1", "tag2"))
            .publishedAt(LocalDate.now())
            .build();

    ResultActions resultActions = perform(post("/books"), command).andExpect(status().isOk());

    CreatedResponse createdResponse = parseResponse(resultActions, CreatedResponse.class);

    assertThat(createdResponse.id()).isNotNull();
  }

  @Test
  void should_batch_create_book() throws Exception {
    CreateBookCommand command =
        CreateBookCommand.builder()
            .title("西游记")
            .author("吴承恩")
            .price(BigDecimal.ONE)
            .asin("asin-1234")
            .tags(List.of("tag1", "tag2"))
            .publishedAt(LocalDate.now())
            .build();

    ResultActions resultActions =
        perform(post("/books/batch"), List.of(command, command)).andExpect(status().isOk());

    List<Book> books = parseResponse(resultActions, new TypeReference<>() {});
    assertThat(books).hasSize(2);
    books.forEach(
        book -> {
          assertThat(book.getId()).isNotNull();
          assertThat(book.getTitle()).isEqualTo("西游记");
          assertThat(book.getAuthor()).isEqualTo("吴承恩");
          assertThat(book.getPrice()).isEqualByComparingTo(BigDecimal.ONE);
          assertThat(book.getAsin()).isEqualTo("asin-1234");
        });
  }

  @Test
  @Sql("/db/book/two-books-with-tags.sql")
  void should_update_description_and_get_book_by_id() throws Exception {
    String bookId = "c0a81ff2-8581-1dc7-8185-817e88ba0000";
    String newDescription = "一段描述";
    perform(
            put("/books/{id}/description", bookId),
            new UpdateBookDescriptionRequest(newDescription))
        .andExpect(status().isOk());

    ResultActions resultActions = perform(get("/books/{id}", bookId)).andExpect(status().isOk());
    Book book = parseResponse(resultActions, Book.class);
    assertThat(book.getDescription()).isEqualTo(newDescription);
    assertThat(book.getTags()).hasSize(2);
  }

  @Test
  @Sql("/db/book/two-books-with-tags.sql")
  void should_update_tags_and_get_book_by_id() throws Exception {
    String bookId = "c0a81ff2-8581-1dc7-8185-817e88ba0000";
    String tag = "新标签";
    perform(put("/books/{id}/tags", bookId), List.of(tag)).andExpect(status().isOk());

    ResultActions resultActions = perform(get("/books/{id}", bookId)).andExpect(status().isOk());
    Book book = parseResponse(resultActions, Book.class);
    assertThat(book.getTags()).hasSize(1).contains(new BookTag(tag, bookId));
  }

  @Test
  @Sql("/db/book/not-visible-book-with-tags.sql")
  void should_show_book() throws Exception {
    String bookId = "c0a81ff2-8581-1dc7-8185-817e88ba0000";
    perform(post("/books/{id}/show", bookId)).andExpect(status().isOk());

    ResultActions resultActions = perform(get("/books/{id}", bookId)).andExpect(status().isOk());
    Book book = parseResponse(resultActions, Book.class);
    assertThat(book.isVisible()).isTrue();
    assertThat(book.getVisibleAt()).isNotNull();
    assertThat(book.getTags()).hasSize(2);
  }

  @Test
  @Sql("/db/book/not-visible-book-with-tags.sql")
  void should_delete_book() throws Exception {
    String bookId = "c0a81ff2-8581-1dc7-8185-817e88ba0000";
    perform(delete("/books/{id}", bookId)).andExpect(status().isOk());

    perform(get("/books/{id}", bookId)).andExpect(status().isNotFound());
  }

  @Test
  @Sql("/db/book/two-books-with-tags.sql")
  void should_batch_delete_books() throws Exception {
    String bookId1 = "c0a81ff2-8581-1dc7-8185-817e88ba0000";
    String bookId2 = "a04b02de-8d11-11ed-afd7-bbbbb937f043";
    perform(delete("/books/batch"), List.of(bookId1, bookId2)).andExpect(status().isOk());

    ResultActions resultActions =
        perform(get("/books").param("page", "1").param("size", "10")).andExpect(status().isOk());

    PageResult<Book> bookPageResult = parseResponse(resultActions, new TypeReference<>() {});

    assertThat(bookPageResult.total()).isEqualTo(0);
    assertThat(bookPageResult.data()).hasSize(0);
  }

  @Test
  @Sql("/db/book/two-books-with-tags.sql")
  void should_get_all_books_by_condition() throws Exception {
    String bookId1 = "c0a81ff2-8581-1dc7-8185-817e88ba0000";
    String bookId2 = "a04b02de-8d11-11ed-afd7-bbbbb937f043";
    ResultActions resultActions =
        perform(
                get("/books/all")
                    .param("ids", bookId1)
                    .param("ids", bookId2)
                    .param("isVisible", "true"))
            .andExpect(status().isOk());

    List<Book> bookPageResult = parseResponse(resultActions, new TypeReference<>() {});

    assertThat(bookPageResult).hasSize(1);
    assertThat(bookPageResult.get(0).getId()).isEqualTo(bookId2);
    assertThat(bookPageResult.get(0).getTags()).hasSize(1);
  }
}
