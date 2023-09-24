package com.zerobase.cafebom.admin.product.dto;

import com.zerobase.cafebom.front.product.domain.Option;
import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class AdminOptionForm {

    @ApiModel(value = "OptionFormRequest")
    @Getter
    public static class Request {

        @Schema(description = "옵션카테고리 아이디", example = "1")
        @NotNull
        private Integer optionCategoryId;

        @Schema(description = "옵션명", example = "아이스")
        @Pattern(regexp = "^[가-힣a-zA-Z]+$", message = "상품명은 한글, 영어 대소문자만 입력해야 합니다.")
        @Size(min = 1, max = 20, message = "옵션명은 1~20 자리로 입력해야 합니다.")
        @NotBlank
        private String name;

        @Schema(description = "옵션 가격", example = "500")
        @NotNull
        private Integer price;
    }

    @ApiModel(value = "OptionFormResponse")
    @Getter
    @Builder
    public static class Response {

        private Integer id;

        private Integer optionCategoryId;

        private String name;

        private Integer price;

        public static AdminOptionForm.Response from(Option option) {
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
