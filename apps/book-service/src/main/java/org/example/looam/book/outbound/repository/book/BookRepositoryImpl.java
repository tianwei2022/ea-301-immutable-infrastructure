package org.example.looam.book.outbound.repository.book;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPQLTemplates;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import org.example.looam.book.domain.book.Book;
import org.example.looam.book.domain.book.BookQuery;
import org.example.looam.book.domain.book.BookRepository;
import org.example.looam.book.domain.book.BookTag;
import org.example.looam.book.outbound.repository.BaseRepository;
import org.example.looam.common.dto.PageQuery;
import org.example.looam.common.dto.PageResult;

import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.group.GroupBy.list;

import static org.example.looam.book.outbound.repository.book.QBookPO.bookPO;
import static org.example.looam.book.outbound.repository.book.QBookTagPO.bookTagPO;

@Repository
@RequiredArgsConstructor
public class BookRepositoryImpl extends BaseRepository implements BookRepository {
  private final BookJpaRepository bookJpaRepository;
  private final BookTagJpaRepository bookTagJpaRepository;
  private final BookPOMapper mapper = BookPOMapper.MAPPER;

  @PersistenceContext private final EntityManager entityManager;

  private JPAQueryFactory jpaQueryFactory;

  @PostConstruct
  public void initQueryFactory() {
    jpaQueryFactory = new JPAQueryFactory(JPQLTemplates.DEFAULT, entityManager);
  }

  @Override
  public Book save(Book book) {
    BookPO bookPO = bookJpaRepository.save(mapper.toPO(book));
    book.getTags().forEach(tag -> tag.setBookId(bookPO.getId()));
    saveBookTags(book.getTags());
    Book savedBook = mapper.toModel(bookPO);
    savedBook.setTags(book.getTags());
    return savedBook;
  }

  private void saveBookTags(List<BookTag> bookTags) {
    List<String> bookIds = bookTags.stream().map(BookTag::getBookId).distinct().toList();
    List<BookTagPO> toDelete =
        jpaQueryFactory.selectFrom(bookTagPO).where(bookTagPO.bookId.in(bookIds)).fetch();
    if (!CollectionUtils.isEmpty(toDelete)) {
      bookTagJpaRepository.deleteAll(toDelete);
    }
    List<BookTagPO> bookTagPOS = bookTags.stream().map(mapper::toPO).toList();
    bookTagJpaRepository.saveAll(bookTagPOS);
  }

  @Override
  public Book saveSelf(Book book) {
    BookPO bookPO = bookJpaRepository.save(mapper.toPO(book));
    return mapper.toModel(bookPO);
  }

  @Override
  public List<Book> saveAll(List<Book> books) {
    books.forEach(
        book -> {
          if (book.getId() == null) {
            book.setId(UUID.randomUUID().toString());
          }
          book.getTags().forEach(tag -> tag.setBookId(book.getId()));
        });
    List<Book> saved =
        bookJpaRepository.saveAll(books.stream().map(mapper::toPO).toList()).stream()
            .map(mapper::toModel)
            .toList();
    Map<String, List<BookTag>> bookTagsMap =
        books.stream().collect(Collectors.toMap(Book::getId, Book::getTags));
    saveBookTags(books.stream().map(Book::getTags).flatMap(List::stream).toList());
    saved.forEach(book -> book.setTags(bookTagsMap.getOrDefault(book.getId(), List.of())));
    return saved;
  }

  @Override
  public void delete(Book book) {
    BookPO bookPO = mapper.toPO(book);
    bookPO.setDeleted(true);
    bookJpaRepository.save(bookPO);
  }

  @Override
  public void deleteAll(List<Book> books) {
    List<BookPO> bookPOS = books.stream().map(mapper::toPO).toList();
    bookPOS.forEach(bookPO -> bookPO.setDeleted(true));
    bookJpaRepository.saveAll(bookPOS);
  }

  @Override
  public Optional<Book> findById(String id) {
    Optional<Book> bookOptional =
        bookJpaRepository.findById(id).stream()
            .filter(po -> !po.isDeleted())
            .findAny()
            .map(mapper::toModel);
    bookOptional.ifPresent(
        book ->
            book.setTags(
                findBookTags(List.of(book.getId())).getOrDefault(book.getId(), List.of())));
    return bookOptional;
  }

  private Map<String, List<BookTag>> findBookTags(List<String> bookIds) {
    Map<String, List<BookTagPO>> transform =
        jpaQueryFactory
            .from(bookTagPO)
            .where(bookTagPO.bookId.in(bookIds))
            .transform(groupBy(bookTagPO.bookId).as(list(bookTagPO)));
    return transform.entrySet().stream()
        .collect(
            Collectors.toMap(
                Map.Entry::getKey,
                entry -> entry.getValue().stream().map(mapper::toModel).toList()));
  }

  @Override
  public List<Book> findAllByQuery(BookQuery query) {
    List<Book> books =
        jpaQueryFactory.selectFrom(bookPO).where(buildPredicate(query)).fetch().stream()
            .map(mapper::toModel)
            .toList();
    queryAndAssembleBookTag(books);
    return books;
  }

  private void queryAndAssembleBookTag(List<Book> books) {
    List<String> ids = books.stream().map(Book::getId).toList();
    Map<String, List<BookTag>> bookTags = findBookTags(ids);
    books.forEach(book -> book.setTags(bookTags.getOrDefault(book.getId(), List.of())));
  }

  @Override
  public PageResult<Book> findPageByQuery(BookQuery query, PageQuery pageQuery) {
    BooleanBuilder predicate = buildPredicate(query);
    List<Book> books =
        jpaQueryFactory
            .selectFrom(bookPO)
            .where(predicate)
            .offset(pageQuery.page() - 1)
            .limit(pageQuery.size())
            .fetch()
            .stream()
            .map(mapper::toModel)
            .toList();
    Long count =
        jpaQueryFactory.select(bookPO.id.countDistinct()).from(bookPO).where(predicate).fetchOne();
    queryAndAssembleBookTag(books);
    return new PageResult<>(books, count == null ? 0 : count);
  }

  protected BooleanBuilder buildPredicate(BookQuery query) {
    BooleanBuilder booleanBuilder = new BooleanBuilder();
    booleanBuilder.and(bookPO.deleted.eq(false));
    if (query.id() != null) {
      booleanBuilder.and(bookPO.id.eq(query.id()));
    }
    if (query.ids() != null) {
      booleanBuilder.and(bookPO.id.in(query.ids()));
    }
    if (query.isVisible() != null) {
      booleanBuilder.and(bookPO.visible.eq(query.isVisible()));
    }
    if (query.publishedAt() != null) {
      booleanBuilder.and(bookPO.publishedAt.eq(query.publishedAt()));
    }
    if (query.searchKey() != null) {
      BooleanExpression searchExpression =
          bookPO
              .title
              .like("%" + query.searchKey() + "%")
              .or(bookPO.description.like("%" + query.searchKey() + "%"));
      booleanBuilder.and(searchExpression);
    }
    return booleanBuilder;
  }
}
