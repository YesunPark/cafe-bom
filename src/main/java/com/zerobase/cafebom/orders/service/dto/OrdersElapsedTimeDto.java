package com.zerobase.cafebom.orders.service.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OrdersElapsedTimeDto {

    private Long elapsedTimeMinutes;
}