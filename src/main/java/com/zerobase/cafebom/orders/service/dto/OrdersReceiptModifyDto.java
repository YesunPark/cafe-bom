package com.zerobase.cafebom.orders.service.dto;

import com.zerobase.cafebom.orders.controller.form.OrdersReceiptModifyForm;
import com.zerobase.cafebom.orders.domain.type.OrdersReceiptStatus;
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