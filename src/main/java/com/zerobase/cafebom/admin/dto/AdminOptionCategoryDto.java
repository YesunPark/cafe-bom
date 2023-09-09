package com.zerobase.cafebom.admin.dto;

import lombok.Builder;
import lombok.Getter;

public class AdminOptionCategoryDto {

    @Getter
    @Builder
    public static class Request {

        String name;

        public static AdminOptionCategoryDto.Request from(AdminOptionCategoryForm.Request request) {
            return Request.builder()
                    .name(request.getName())
                    .build();
        }
    }
}
