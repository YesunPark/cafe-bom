package com.zerobase.cafebom.orders.controller.form;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OrdersElapsedTimeForm {

    private Long elapsedTimeMinutes;
}