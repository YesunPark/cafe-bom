package com.zerobase.cafebom.option.domain.type;

import com.zerobase.cafebom.option.domain.entity.Option;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OptionDto {

    private Integer id;

    private Integer optionCategory;

    private String name;

    private Integer price;

    public static OptionDto from(Option option) {
        OptionDto optionDto = OptionDto.builder()
                .id(option.getId())
                .optionCategory(option.getOptionCategory().getId())
                .name(option.getName())
                .price(option.getPrice())
                .build();
        return optionDto;
    }

}
