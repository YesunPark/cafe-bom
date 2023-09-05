package com.zerobase.cafebom.orders.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OrdersElapsedFindForm {

    private Long elapsedTimeMinutes;
}