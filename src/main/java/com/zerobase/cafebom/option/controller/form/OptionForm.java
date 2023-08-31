package com.zerobase.cafebom.option.controller.form;

import com.zerobase.cafebom.option.domain.entity.Option;
import lombok.Builder;
import lombok.Getter;

public class OptionForm {

    @Getter
    @Builder
    public static class Response {

        private Integer id;

        private Integer optionCategoryId;

        private String name;

        private Integer price;

        public static OptionForm.Response from(Option option) {
            Response response = Response.builder()
                    .id(option.getId())
                    .optionCategoryId(option.getOptionCategory().getId())
                    .name(option.getName())
                    .price(option.getPrice())
                    .build();
            return response;
        }

    }
}
