package org.example.looam.book.domain.book;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import org.example.looam.book.domain.book.command.CreateBookCommand;

import static org.assertj.core.api.Assertions.assertThat;

class BookTest {

  @Test
  void create_book_tags_should_not_be_null_given_null_in_command() {
    Book book =
        Book.create(
            CreateBookCommand.builder()
                .title("西游记")
                .author("吴承恩")
                .price(BigDecimal.ONE)
                .asin("asin-1234")
                .publishedAt(LocalDate.now())
                .build());

    assertThat(book.getTags()).isNotNull();
  }
}
