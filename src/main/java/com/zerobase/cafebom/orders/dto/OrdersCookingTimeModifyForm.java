package com.zerobase.cafebom.orders.dto;

import com.zerobase.cafebom.type.OrdersCookingTime;
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
