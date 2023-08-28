package com.zerobase.cafebom.orders.controller.form;

import com.zerobase.cafebom.orders.domain.type.OrdersCookingStatus;
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