package com.zerobase.cafebom.orders.dto;

import com.zerobase.cafebom.type.OrdersCookingStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrdersStatusModifyForm {

    private OrdersCookingStatus newStatus;
}