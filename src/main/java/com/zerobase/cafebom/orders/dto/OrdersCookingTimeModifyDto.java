package com.zerobase.cafebom.orders.dto;

import com.zerobase.cafebom.type.OrdersCookingTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OrdersCookingTimeModifyDto {

    private OrdersCookingTime selectedCookingTime;

    public static OrdersCookingTimeModifyDto from(OrdersCookingTimeModifyForm form) {
        return OrdersCookingTimeModifyDto.builder()
            .selectedCookingTime(form.getSelectedCookingTime())
            .build();
    }
}
