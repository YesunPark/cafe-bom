package com.zerobase.cafebom.front.order.dto;

import com.zerobase.cafebom.common.type.OrderCookingStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OrderStatusModifyDto {

    private OrderCookingStatus newStatus;

    public static OrderStatusModifyDto from(OrderStatusModifyForm orderStatusModifyForm) {
        return OrderStatusModifyDto.builder()
            .newStatus(orderStatusModifyForm.getNewStatus())
            .build();
    }
}
