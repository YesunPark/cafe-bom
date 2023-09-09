package com.zerobase.cafebom.admin.dto;

import com.zerobase.cafebom.optioncategory.domain.OptionCategory;
import lombok.Builder;
import lombok.Getter;

public class AdminOptionCategoryDto {

    @Getter
    @Builder
    public static class Response {

        private Integer optionCategoryId;

        private String name;

        public static AdminOptionCategoryDto.Response from(OptionCategory optionCategory){
            return Response.builder()
                    .optionCategoryId(optionCategory.getId())
                    .name(optionCategory.getName())
                    .build();
        }

    }
}
