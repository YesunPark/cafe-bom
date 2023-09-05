package com.zerobase.cafebom.orders.dto;

import com.zerobase.cafebom.type.OrdersCookingStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OrdersStatusModifyDto {

    private OrdersCookingStatus newStatus;

    public static OrdersStatusModifyDto from(OrdersStatusModifyForm ordersStatusModifyForm) {
        return OrdersStatusModifyDto.builder()
            .newStatus(ordersStatusModifyForm.getNewStatus())
            .build();
    }
}
