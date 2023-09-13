package com.zerobase.cafebom.cart.dto;

import com.zerobase.cafebom.option.domain.Option;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class CartListOptionDto {

    private Integer optionId;

    private String optionName;

    private Integer optionPrice;

    public static CartListOptionDto from(Option option) {
        return CartListOptionDto.builder()
            .optionId(option.getId())
            .optionName(option.getName())
            .optionPrice(option.getPrice())
            .build();
    }

}
