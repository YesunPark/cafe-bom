package com.zerobase.cafebom.orders.domain.entity;

import com.zerobase.cafebom.common.BaseTimeEntity;
import com.zerobase.cafebom.orders.domain.type.OrdersCookingTime;
import com.zerobase.cafebom.orders.domain.type.OrdersStatus;
import com.zerobase.cafebom.member.domain.entity.Member;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "`order`")
public class Order extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "member_id")
  private Member member;

  @NotNull
  private String payment;

  @NotNull
  @Enumerated(EnumType.STRING)
  private OrdersStatus status;

  @Enumerated(EnumType.STRING)
  private OrdersCookingTime cookingTime;

  @NotNull
  private boolean isCanceled;
}