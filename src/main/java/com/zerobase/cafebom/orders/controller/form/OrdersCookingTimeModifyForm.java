package com.zerobase.cafebom.orders.controller.form;

import com.zerobase.cafebom.orders.domain.type.OrdersCookingTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrdersCookingTimeModifyForm {

    private OrdersCookingTime selectedCookingTime;
}
