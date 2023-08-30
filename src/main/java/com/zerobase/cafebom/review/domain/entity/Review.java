package com.zerobase.cafebom.review.domain.entity;

import com.zerobase.cafebom.common.BaseTimeEntity;
import com.zerobase.cafebom.member.domain.entity.Member;
import com.zerobase.cafebom.orders.domain.type.OrdersCookingStatus;
import com.zerobase.cafebom.orders.domain.type.OrdersCookingTime;
import com.zerobase.cafebom.orders.domain.type.OrdersReceiptStatus;
import com.zerobase.cafebom.orders.domain.type.Payment;
import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
public class Review extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    private Long memberId;
//
//    @NotNull
//    @Enumerated(EnumType.STRING)
//    private Payment payment;
//
//    @NotNull
//    @Enumerated(EnumType.STRING)
//    private OrdersCookingStatus cookingStatus;
//
//    @Enumerated(EnumType.STRING)
//    private OrdersCookingTime cookingTime;
//
//    @NotNull
//    @Enumerated(EnumType.STRING)
//    private OrdersReceiptStatus receiptStatus;
//
//    private LocalDateTime receivedTime;

}