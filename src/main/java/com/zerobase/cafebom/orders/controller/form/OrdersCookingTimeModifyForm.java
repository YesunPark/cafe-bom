package com.zerobase.cafebom.orders.controller.form;

import com.zerobase.cafebom.orders.domain.type.OrdersCookingTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OrdersCookingTimeModifyForm {

    private OrdersCookingTime selectedCookingTime;
}
