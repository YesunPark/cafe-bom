package com.zerobase.cafebom.product.controller.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Builder
public class SoldOutStatusForm {

    @Schema(description = "품절여부", example = "SOLD_OUT")
    @Pattern(regexp = "^[A-Z_]+$", message = "품절여부는 영어 대문자와 언더스코어(_)만 입력 가능합니다")
    @NotBlank(message = "품절여부는 필수로 입력해야 합니다.")
    private String soldOutStatus;

}
