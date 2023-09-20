package com.zerobase.cafebom.front.order.dto;

import com.zerobase.cafebom.common.type.OrderCookingStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OrdersStatusModifyDto {

    private OrderCookingStatus newStatus;

    public static OrdersStatusModifyDto from(OrdersStatusModifyForm ordersStatusModifyForm) {
        return OrdersStatusModifyDto.builder()
            .newStatus(ordersStatusModifyForm.getNewStatus())
            .build();
    }
}
