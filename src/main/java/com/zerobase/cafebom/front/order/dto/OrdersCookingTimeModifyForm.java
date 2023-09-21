package com.zerobase.cafebom.front.order.dto;

import com.zerobase.cafebom.common.type.OrderCookingTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrdersCookingTimeModifyForm {

    private OrderCookingTime selectedCookingTime;
}
