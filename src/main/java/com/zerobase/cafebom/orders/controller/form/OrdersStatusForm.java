package com.zerobase.cafebom.orders.controller.form;

import com.zerobase.cafebom.orders.domain.type.OrdersCookingStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrdersStatusForm {

    private OrdersCookingStatus newStatus;
}