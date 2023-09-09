package com.zerobase.cafebom.admin.dto;

import lombok.Builder;
import lombok.Getter;

public class AdminOptionCategoryForm {

    @Getter
    @Builder
    public static class Response {

        private Integer optionCategoryId;

        private String name;

        public static AdminOptionCategoryForm.Response
        from(AdminOptionCategoryDto.Response adminOptionCategoryDto) {
            return Response.builder()
                    .optionCategoryId(adminOptionCategoryDto.getOptionCategoryId())
                    .name(adminOptionCategoryDto.getName())
                    .build();
        }
    }
}
