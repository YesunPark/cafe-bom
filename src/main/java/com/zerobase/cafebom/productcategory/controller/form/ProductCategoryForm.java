package com.zerobase.cafebom.productcategory.controller.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class ProductCategoryForm {

    @Getter
    public static class Response{

        private String name;
    }

    @Getter
    public static class Request{
        @Schema(description = "상품 카테고리명", example = "커피")
        @Pattern(regexp = "^[가-힣a-zA-Z]+$", message = "상품 카테고리명은 한글, 영어 대소문자만 입력해야 합니다.")
        @Size(min = 1,max = 20,message = "상품 카테고리명은 1~20 자리로 입력해야 합니다.")
        private String name;
    }

}
