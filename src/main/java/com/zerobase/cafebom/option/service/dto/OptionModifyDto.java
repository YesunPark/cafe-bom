package com.zerobase.cafebom.option.service.dto;

import com.zerobase.cafebom.option.controller.form.OptionModifyForm;
import lombok.Builder;
import lombok.Getter;

public class OptionModifyDto {

    @Getter
    @Builder
    public static class Request {

        private Integer optionCategoryId;

        private String name;

        private Integer price;

        public static OptionModifyDto.Request from(OptionModifyForm form) {
            return Request.builder()
                    .optionCategoryId(form.getOptionCategoryId())
                    .name(form.getName())
                    .price(form.getPrice())
                    .build();
        }
    }

}
