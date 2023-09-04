package com.zerobase.cafebom.option.controller.form;

import com.zerobase.cafebom.option.domain.entity.Option;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;

public class OptionForm {

    @Getter
    public static class Request {

        @Schema(description = "옵션카테고리 아이디", example = "1")
        @Size(min = 1, max = 10, message = "옵션카테고리 아이디는 1~10 자리로 입력해야 합니다.")
        private Integer optionCategoryId;

        @Schema(description = "옵션명", example = "아이스")
        @Pattern(regexp = "^[가-힣a-zA-Z]+$", message = "상품명은 한글, 영어 대소문자만 입력해야 합니다.")
        @Size(min = 1, max = 20, message = "옵션명은 1~20 자리로 입력해야 합니다.")
        private String name;

        @Schema(description = "옵션 가격", example = "500")
        @Pattern(regexp = "^[0-9]+$", message = "옵션 가격은 숫자만 입력 가능합니다.")
        @Size(min = 1, max = 10, message = "옵션 가격 1~10 자리로 입력해야 합니다.")
        private Integer price;
    }

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
