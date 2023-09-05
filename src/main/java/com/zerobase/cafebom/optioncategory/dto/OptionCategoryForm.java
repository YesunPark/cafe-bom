package com.zerobase.cafebom.optioncategory.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class OptionCategoryForm {

    @Getter
    @Builder
    public static class Request {
        @Schema(description = "옵션 카테고리명", example = "샷추가")
        @Pattern(regexp = "^[가-힣a-zA-Z]+$", message = "옵션 카테고리명은 한글, 영어 대소문자만 입력해야 합니다.")
        @Size(min = 1, max = 20, message = "옵션 카테고리명은 1~20 자리로 입력해야 합니다.")
        private String name;
    }

}
