package com.zerobase.cafebom.front.order.dto;

import com.zerobase.cafebom.common.type.OrdersReceiptStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OrdersReceiptModifyDto {

    private OrdersReceiptStatus newReceiptStatus;

    public static OrdersReceiptModifyDto from(OrdersReceiptModifyForm ordersReceiptModifyForm) {
        return OrdersReceiptModifyDto.builder()
            .newReceiptStatus(ordersReceiptModifyForm.getNewReceiptStatus())
            .build();
    }
}