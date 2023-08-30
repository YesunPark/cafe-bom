package com.zerobase.cafebom.option.service.dto;

import com.zerobase.cafebom.option.domain.entity.Option;
import com.zerobase.cafebom.optioncategory.domain.entity.OptionCategory;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OptionAddDto {

    private Integer id;

    private OptionCategory optionCategory;

    private String name;

    private Integer price;

    public static OptionAddDto from(Option option) {
        return OptionAddDto.builder()
                .id(option.getId())
                .optionCategory(option.getOptionCategory())
                .name(option.getName())
                .price(option.getPrice())
                .build();
    }
}
