package org.example.looam.order.outbound.repository.order;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLTemplates;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import org.example.looam.common.dto.PageQuery;
import org.example.looam.common.dto.PageResult;
import org.example.looam.order.domain.order.Order;
import org.example.looam.order.domain.order.OrderItem;
import org.example.looam.order.domain.order.OrderQuery;
import org.example.looam.order.domain.order.OrderRepository;
import org.example.looam.order.outbound.repository.BaseRepository;

import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.group.GroupBy.list;

import static org.example.looam.order.outbound.repository.order.QOrderItemPO.orderItemPO;
import static org.example.looam.order.outbound.repository.order.QOrderPO.orderPO;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryImpl extends BaseRepository implements OrderRepository {
  private final OrderJpaRepository orderJpaRepository;
  private final OrderItemJpaRepository orderItemJpaRepository;
  private final OrderPOMapper mapper = OrderPOMapper.MAPPER;

  @PersistenceContext private final EntityManager entityManager;

  private JPAQueryFactory jpaQueryFactory;

  @PostConstruct
  public void initQueryFactory() {
    jpaQueryFactory = new JPAQueryFactory(JPQLTemplates.DEFAULT, entityManager);
  }

  @Override
  public Order save(Order order) {
    OrderPO orderPO = orderJpaRepository.save(mapper.toPO(order));
    order.getOrderItems().forEach(item -> item.setOrderId(orderPO.getId()));
    saveOrderItems(order.getOrderItems());
    Order savedOrder = mapper.toModel(orderPO);
    savedOrder.setOrderItems(order.getOrderItems());
    return savedOrder;
  }

  private void saveOrderItems(List<OrderItem> orderItems) {
    List<OrderItemPO> orderItemPOS = orderItems.stream().map(mapper::toPO).toList();
    orderItemJpaRepository.saveAll(orderItemPOS);
  }

  @Override
  public Order saveSelf(Order order) {
    OrderPO orderPO = orderJpaRepository.save(mapper.toPO(order));
    return mapper.toModel(orderPO);
  }

  @Override
  public Optional<Order> findById(String id) {
    Optional<Order> orderOptional = orderJpaRepository.findById(id).map(mapper::toModel);
    orderOptional.ifPresent(
        order ->
            order.setOrderItems(
                findOrderItems(List.of(order.getId())).getOrDefault(order.getId(), List.of())));
    return orderOptional;
  }

  private Map<String, List<OrderItem>> findOrderItems(List<String> orderIds) {
    Map<String, List<OrderItemPO>> transform =
        jpaQueryFactory
            .from(orderItemPO)
            .where(orderItemPO.orderId.in(orderIds))
            .transform(groupBy(orderItemPO.orderId).as(list(orderItemPO)));
    return transform.entrySet().stream()
        .collect(
            Collectors.toMap(
                Map.Entry::getKey,
                entry -> entry.getValue().stream().map(mapper::toModel).toList()));
  }

  private void queryAndAssembleOrderItem(List<Order> orders) {
    List<String> ids = orders.stream().map(Order::getId).toList();
    Map<String, List<OrderItem>> orderItems = findOrderItems(ids);
    orders.forEach(order -> order.setOrderItems(orderItems.getOrDefault(order.getId(), List.of())));
  }

  @Override
  public PageResult<Order> findPageByQuery(OrderQuery query, PageQuery pageQuery) {
    BooleanBuilder predicate = buildPredicate(query);
    List<Order> orders =
        jpaQueryFactory
            .selectFrom(orderPO)
            .where(predicate)
            .offset(pageQuery.page() - 1)
            .limit(pageQuery.size())
            .fetch()
            .stream()
            .map(mapper::toModel)
            .toList();
    Long count =
        jpaQueryFactory
            .select(orderPO.id.countDistinct())
            .from(orderPO)
            .where(predicate)
            .fetchOne();
    queryAndAssembleOrderItem(orders);
    return new PageResult<>(orders, count == null ? 0 : count);
  }

  protected BooleanBuilder buildPredicate(OrderQuery query) {
    BooleanBuilder booleanBuilder = new BooleanBuilder();
    if (query.orderNumber() != null) {
      booleanBuilder.and(orderPO.orderNumber.eq(query.orderNumber()));
    }
    if (query.userId() != null) {
      booleanBuilder.and(orderPO.userId.eq(query.userId()));
    }
    if (query.orderStatuses() != null) {
      booleanBuilder.and(orderPO.status.in(query.orderStatuses()));
    }
    return booleanBuilder;
  }
}
