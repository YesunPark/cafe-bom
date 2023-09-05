package com.zerobase.cafebom.optioncategory.dto;

import lombok.Builder;
import lombok.Getter;

public class OptionCategoryDto {

    @Getter
    @Builder
    public static class Request {

        String name;

        public static OptionCategoryDto.Request from(OptionCategoryForm.Request request) {
            return Request.builder()
                    .name(request.getName())
                    .build();
        }
    }
}
