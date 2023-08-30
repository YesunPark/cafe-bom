package com.zerobase.cafebom.option.controller.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Builder
@NoArgsConstructor
public class OptionAddForm {

    @Schema(description = "옵션카테고리 아이디", example = "1")
    @Pattern(regexp = "^[0-9]+$", message = "옵션카테고리 아이디는 숫자만 입력 가능합니다.")
    @Size(min = 1,max = 10,message = "옵션카테고리 아이디는 1~10 자리로 입력해야 합니다.")
    private Integer optionCategory;

    @Schema(description = "옵션명", example = "아이스")
    @Pattern(regexp = "^[가-힣a-zA-Z]+$", message = "상품명은 한글, 영어 대소문자만 입력해야 합니다.")
    @Size(min = 1,max = 20,message = "옵션명은 1~20 자리로 입력해야 합니다.")
    private String name;

    @Schema(description = "옵션 가격", example = "500")
    @Pattern(regexp = "^[0-9]+$", message = "옵션 가격은 숫자만 입력 가능합니다.")
    @Size(min = 1,max = 10,message = "옵션 가격 1~10 자리로 입력해야 합니다.")
    private Integer price;

    @Builder
    public OptionAddForm(Integer optionCategory, String name, Integer price) {
        this.optionCategory = optionCategory;
        this.name = name;
        this.price = price;
    }

}
