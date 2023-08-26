package com.zerobase.cafebom.orders.service.dto;

import com.zerobase.cafebom.orders.controller.form.OrdersStatusForm;
import com.zerobase.cafebom.orders.domain.type.OrdersCookingStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OrdersStatusDto {

    private OrdersCookingStatus newStatus;

    public static OrdersStatusDto from(OrdersStatusForm ordersStatusForm) {
        return OrdersStatusDto.builder()
            .newStatus(ordersStatusForm.getNewStatus())
            .build();
    }

}
