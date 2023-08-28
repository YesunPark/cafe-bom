package com.zerobase.cafebom.orders.service.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OrdersElapsedFindDto {

    private Long elapsedTimeMinutes;
}