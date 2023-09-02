package com.zerobase.cafebom.orders.controller.form;

import com.zerobase.cafebom.orders.domain.type.OrdersCookingTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrdersCookingTimeModifyForm {

    private OrdersCookingTime selectedCookingTime;
}
