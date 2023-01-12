package org.example.looam.order.outbound.repository.order;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.annotations.UuidGenerator.Style;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import org.example.looam.order.domain.order.OrderStatus;

import static jakarta.persistence.GenerationType.UUID;

@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "book_order")
public class OrderPO {
  @Id
  @GeneratedValue(strategy = UUID)
  @UuidGenerator(style = Style.TIME)
  private String id;

  private String userId;
  private String orderNumber;
  private BigDecimal totalPrice;

  @Enumerated(EnumType.STRING)
  private OrderStatus status;

  private LocalDateTime completeAt;
  private LocalDateTime cancelAt;

  @CreatedDate
  @Column(updatable = false)
  private LocalDateTime createdAt;

  @LastModifiedDate private LocalDateTime updatedAt;
}
