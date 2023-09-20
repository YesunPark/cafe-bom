package com.zerobase.cafebom.front.order.dto;

import com.zerobase.cafebom.common.type.OrderCookingTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OrdersCookingTimeModifyDto {

    private OrderCookingTime selectedCookingTime;

    public static OrdersCookingTimeModifyDto from(OrdersCookingTimeModifyForm form) {
        return OrdersCookingTimeModifyDto.builder()
            .selectedCookingTime(form.getSelectedCookingTime())
            .build();
    }
}
