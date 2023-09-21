package com.zerobase.cafebom.front.order.dto;

import com.zerobase.cafebom.common.type.OrdersReceiptStatus;
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