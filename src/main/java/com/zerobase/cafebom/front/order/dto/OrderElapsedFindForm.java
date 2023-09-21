package com.zerobase.cafebom.front.order.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OrderElapsedFindForm {

    private Long elapsedTimeMinutes;
}