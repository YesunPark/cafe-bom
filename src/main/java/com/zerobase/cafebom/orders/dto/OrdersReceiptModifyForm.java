package com.zerobase.cafebom.orders.dto;

import com.zerobase.cafebom.type.OrdersReceiptStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrdersReceiptModifyForm {

    private OrdersReceiptStatus newReceiptStatus;
}