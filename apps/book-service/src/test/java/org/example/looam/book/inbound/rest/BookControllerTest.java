package org.example.looam.book.inbound.rest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.ResultActions;

import org.example.looam.book.appservice.BookAppService;
import org.example.looam.book.domain.book.Book;
import org.example.looam.book.domain.book.command.CreateBookCommand;
import org.example.looam.book.inbound.rest.dto.UpdateBookDescriptionRequest;
import org.example.looam.common.dto.CreatedResponse;
import org.example.looam.common.dto.PageQuery;
import org.example.looam.common.dto.PageResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class BookControllerTest extends BaseControllerTest {
  @MockBean private BookAppService bookAppService;

  @Test
  void createBook_should_pass_given_valid_params() throws Exception {
    when(bookAppService.createBook(any())).thenReturn(new CreatedResponse("id"));

    CreateBookCommand command =
        CreateBookCommand.builder()
            .title("西游记")
            .author("吴承恩")
            .price(BigDecimal.ONE)
            .asin("asin-1234")
            .publishedAt(LocalDate.now())
            .build();

    ResultActions resultActions = perform(post("/books"), command).andExpect(status().isOk());

    CreatedResponse createdResponse = parseResponse(resultActions, CreatedResponse.class);

    assertThat(createdResponse).isEqualTo(new CreatedResponse("id"));
  }

  @Test
  void createBook_should_fail_given_price_is_zero() throws Exception {
    CreateBookCommand command =
        CreateBookCommand.builder()
            .title("西游记")
            .author("吴承恩")
            .price(BigDecimal.ZERO) // invalid
            .asin("asin-1234")
            .publishedAt(LocalDate.now())
            .build();

    perform(post("/books"), command).andExpect(status().isBadRequest());
  }

  @Test
  void createBook_should_fail_given_has_blank_tag() throws Exception {
    CreateBookCommand command =
        CreateBookCommand.builder()
            .title("西游记")
            .author("吴承恩")
            .price(BigDecimal.ONE)
            .asin("asin-1234")
            .publishedAt(LocalDate.now())
            .tags(List.of("  ")) // invalid
            .build();

    perform(post("/books"), command).andExpect(status().isBadRequest());
  }

  @Test
  void batchCreateBook_should_fail_given_any_book_is_invalid() throws Exception {
    CreateBookCommand command =
        CreateBookCommand.builder()
            .title("西游记")
            .author("吴承恩")
            .price(BigDecimal.ZERO) // invalid
            .asin("asin-1234")
            .publishedAt(LocalDate.now())
            .tags(List.of("  "))
            .build();

    perform(post("/books/batch"), List.of(command)).andExpect(status().isBadRequest());
  }

  @Test
  void updateBookDescription_should_fail_given_description_is_blank() throws Exception {
    perform(put("/books/1/description"), new UpdateBookDescriptionRequest("  "))
        .andExpect(status().isBadRequest());
  }

  @Test
  void updateBookTags_should_fail_given_has_tag_blank() throws Exception {
    perform(put("/books/1/tags"), List.of("经管类", "  ")).andExpect(status().isBadRequest());
  }

  @Test
  void batchDeleteBook_should_fail_given_invalid_id() throws Exception {
    perform(delete("/books/batch"), List.of("id1", "  ", "id-3"))
        .andExpect(status().isBadRequest());
  }

  @Test
  void findBookPageable_should_success() throws Exception {
    when(bookAppService.findPage(any(), eq(new PageQuery(1, 10))))
        .thenReturn(
            PageResult.<Book>builder()
                .total(2)
                .data(List.of(Book.builder().id("id-1").build(), Book.builder().id("id-2").build()))
                .build());
    ResultActions resultActions =
        perform(get("/books").param("page", "1").param("size", "10")).andExpect(status().isOk());

    PageResult<Book> books = parseResponse(resultActions, new TypeReference<>() {});

    assertThat(books.total()).isEqualTo(2);
    assertThat(books.data()).hasSize(2).extracting("id").contains("id-1", "id-2");
  }

  @Test
  void findBookPageable_should_fail_given_no_page_param() throws Exception {
    perform(get("/books").param("size", "10")).andExpect(status().isBadRequest());
  }
}
