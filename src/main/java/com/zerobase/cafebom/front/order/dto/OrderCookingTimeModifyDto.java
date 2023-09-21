package com.zerobase.cafebom.front.order.dto;

import com.zerobase.cafebom.common.type.OrderCookingTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OrderCookingTimeModifyDto {

    private OrderCookingTime selectedCookingTime;

    public static OrderCookingTimeModifyDto from(OrdersCookingTimeModifyForm form) {
        return OrderCookingTimeModifyDto.builder()
            .selectedCookingTime(form.getSelectedCookingTime())
            .build();
    }
}
