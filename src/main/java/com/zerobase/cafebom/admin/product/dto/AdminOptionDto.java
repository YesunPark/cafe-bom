package com.zerobase.cafebom.admin.product.dto;

import lombok.Builder;
import lombok.Getter;

public class AdminOptionDto {

    @Getter
    @Builder
    public static class Request {

        private Integer optionCategoryId;

        private String name;

        private Integer price;

        public static AdminOptionDto.Request from(AdminOptionForm.Request form) {
            return AdminOptionDto.Request.builder()
                .optionCategoryId(form.getOptionCategoryId())
                .name(form.getName())
                .price(form.getPrice())
                .build();
        }
    }
}
