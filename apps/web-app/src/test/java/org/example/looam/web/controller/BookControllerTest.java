package org.example.looam.web.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.ResultActions;

import org.example.looam.common.dto.CreatedResponse;
import org.example.looam.common.dto.PageResult;
import org.example.looam.web.client.book.BookServiceClient;
import org.example.looam.web.client.book.dto.BookDTO;
import org.example.looam.web.client.book.dto.BookTagDTO;
import org.example.looam.web.client.book.dto.CreateBookCommand;
import org.example.looam.web.client.book.dto.FindBookPageableParams;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class BookControllerTest extends BaseControllerTest {

  @MockBean private BookServiceClient bookServiceClient;

  @Test
  void createBook() throws Exception {
    when(bookServiceClient.createBook(any())).thenReturn(new CreatedResponse("id"));

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
  void updateBookTags() throws Exception {
    List<String> tags = List.of("tag-1", "tag-2");

    String bookId = "c0a81ff2-8581-1dc7-8185-817e88ba0000";
    perform(put("/books/{id}/tags", bookId), tags).andExpect(status().isOk());

    verify(bookServiceClient).updateBookTags(bookId, tags);
  }

  @Test
  void showBook() throws Exception {
    String bookId = "c0a81ff2-8581-1dc7-8185-817e88ba0000";
    perform(post("/books/{id}/show", bookId)).andExpect(status().isOk());

    verify(bookServiceClient).showBook(bookId);
  }

  @Test
  void findBook() throws Exception {
    String bookId = "c0a81ff2-8581-1dc7-8185-817e88ba0000";
    BookDTO expected = buildWithId(bookId);
    when(bookServiceClient.findBook(bookId)).thenReturn(expected);
    ResultActions resultActions = perform(get("/books/{id}", bookId)).andExpect(status().isOk());
    BookDTO result = parseResponse(resultActions, BookDTO.class);
    assertThat(result).isEqualTo(expected);
  }

  @Test
  void findBookPageable() throws Exception {
    PageResult<BookDTO> expected =
        new PageResult<>(List.of(buildWithId(UUID.randomUUID().toString())), 1);
    when(bookServiceClient.findBookPageable(new FindBookPageableParams(null, true, 1, 10)))
        .thenReturn(expected);

    ResultActions resultActions = perform(get("/books")).andExpect(status().isOk());

    PageResult<BookDTO> result = parseResponse(resultActions, new TypeReference<>() {});
    assertThat(result).isEqualTo(expected);
  }

  private static BookDTO buildWithId(String bookId) {
    return BookDTO.builder()
        .id(bookId)
        .author("吴承恩")
        .title("西游记")
        .price(BigDecimal.ONE)
        .asin("asin-1234")
        .visible(true)
        .publishedAt(LocalDate.now())
        .tags(List.of(new BookTagDTO("小说"), new BookTagDTO("古典文学"), new BookTagDTO("四大名著")))
        .build();
  }
}
