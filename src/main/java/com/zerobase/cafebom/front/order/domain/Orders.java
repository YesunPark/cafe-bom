package com.zerobase.cafebom.front.order.domain;

import com.zerobase.cafebom.common.BaseTimeEntity;
import com.zerobase.cafebom.front.member.domain.Member;
import com.zerobase.cafebom.front.order.dto.OrdersAddDto;
import com.zerobase.cafebom.common.type.OrderCookingStatus;
import com.zerobase.cafebom.common.type.OrderCookingTime;
import com.zerobase.cafebom.common.type.OrdersReceiptStatus;
import com.zerobase.cafebom.common.type.Payment;
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
    private OrderCookingStatus cookingStatus;

    @NotNull
    @Enumerated(EnumType.STRING)
    private OrderCookingTime cookingTime;

    @NotNull
    @Enumerated(EnumType.STRING)
    private OrdersReceiptStatus receiptStatus;

    private LocalDateTime receivedTime;

    public void modifyReceivedTime(OrderCookingStatus newStatus) {
        if (this.cookingStatus == OrderCookingStatus.NONE
            && newStatus == OrderCookingStatus.COOKING) {
            this.receivedTime = LocalDateTime.now();
        }
        this.cookingStatus = newStatus;
    }

    public void modifyReceiptStatus(OrdersReceiptStatus newReceiptStatus) {
        this.receiptStatus = newReceiptStatus;
    }

    public void modifyCookingTime(OrderCookingTime selectedCookingTime) {
        this.cookingTime = selectedCookingTime;
    }

    public static Orders fromAddOrdersDto(OrdersAddDto.Request dto, Member member) {
        return Orders.builder()
            .member(member)
            .payment(dto.getPayment())
            .cookingStatus(OrderCookingStatus.NONE)
            .cookingTime(null)
            .receiptStatus(OrdersReceiptStatus.WAITING)
            .build();
    }
}