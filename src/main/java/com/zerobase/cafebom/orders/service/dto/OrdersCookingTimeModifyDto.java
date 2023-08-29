package com.zerobase.cafebom.orders.service.dto;

import com.zerobase.cafebom.orders.controller.form.OrdersCookingTimeModifyForm;
import com.zerobase.cafebom.orders.domain.type.OrdersCookingTime;
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
