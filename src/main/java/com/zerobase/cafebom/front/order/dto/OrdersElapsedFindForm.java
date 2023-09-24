package com.zerobase.cafebom.front.order.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OrdersElapsedFindForm {

    private Long elapsedTimeMinutes;
}