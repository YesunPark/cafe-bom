package com.zerobase.cafebom.admin.product.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class AdminOptionCategoryForm {

    @ApiModel(value = "AdminOptionCategoryFormRequest")
    @Getter
    public static class Request {
        @Schema(description = "옵션 카테고리명", example = "샷추가")
        @Pattern(regexp = "^[가-힣a-zA-Z]+$", message = "옵션 카테고리명은 한글, 영어 대소문자만 입력해야 합니다.")
        @Size(min = 1, max = 20, message = "옵션 카테고리명은 1~20 자리로 입력해야 합니다.")
        @NotBlank
        private String name;
    }

    @ApiModel(value ="AdminOptionCategoryFormResponse" )
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
