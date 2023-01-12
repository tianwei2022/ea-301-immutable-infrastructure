package org.example.looam.book.domain.book;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.example.looam.book.domain.book.command.CreateBookCommand;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Book {
  private String id;
  private String title;
  private String description;
  private String author;
  private String asin;
  private BigDecimal price;
  private LocalDate publishedAt;
  private List<BookTag> tags;
  private boolean visible;
  private LocalDateTime visibleAt;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;

  public static Book create(CreateBookCommand createBookCommand) {
    Book book = BookMapper.MAPPER.toModel(createBookCommand);
    if (book.getTags() == null) {
      book.tags = List.of();
    }
    return book;
  }

  public void updateDescription(String description) {
    this.description = description;
  }

  public void updateTags(List<String> tags) {
    this.tags = tags.stream().map(BookMapper.MAPPER::toBookTag).toList();
  }

  public void show() {
    this.visible = true;
    this.visibleAt = LocalDateTime.now();
  }
}
