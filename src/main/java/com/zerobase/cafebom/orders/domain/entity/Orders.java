package com.zerobase.cafebom.orders.domain.entity;

import com.zerobase.cafebom.common.BaseTimeEntity;
import com.zerobase.cafebom.member.domain.entity.Member;
import com.zerobase.cafebom.orders.domain.type.OrdersCookingStatus;
import com.zerobase.cafebom.orders.domain.type.OrdersCookingTime;
import com.zerobase.cafebom.orders.domain.type.OrdersReceiptStatus;
import com.zerobase.cafebom.orders.domain.type.Payment;
import com.zerobase.cafebom.orders.service.dto.OrdersAddDto;
import java.time.LocalDateTime;
import javax.persistence.*;
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
public class Orders extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Payment payment;

    @NotNull
    @Enumerated(EnumType.STRING)
    private OrdersCookingStatus cookingStatus;

    @Enumerated(EnumType.STRING)
    private OrdersCookingTime cookingTime;

    @NotNull
    @Enumerated(EnumType.STRING)
    private OrdersReceiptStatus receiptStatus;

    private LocalDateTime receivedTime;


    public void modifyReceivedTime(OrdersCookingStatus newStatus) {
        if (this.cookingStatus == OrdersCookingStatus.NONE
            && newStatus == OrdersCookingStatus.COOKING) {
            this.receivedTime = LocalDateTime.now();
        }
        this.cookingStatus = newStatus;
    }

    public static Orders fromAddOrdersDto(OrdersAddDto.Request dto, Member member) {
        return Orders.builder()
            .member(member)
            .payment(dto.getPayment())
            .cookingStatus(OrdersCookingStatus.NONE)
            .cookingTime(null)
            .receiptStatus(OrdersReceiptStatus.WAITING)
            .build();
    }
}