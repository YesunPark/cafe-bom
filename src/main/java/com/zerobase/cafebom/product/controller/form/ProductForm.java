package com.zerobase.cafebom.product.controller.form;

import com.zerobase.cafebom.product.domain.entity.SoldOutStatus;
import com.zerobase.cafebom.productcategory.domain.entity.ProductCategory;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Builder
public class ProductForm {

    private ProductCategory productCategoryId;

    @Schema(description = "상품명", example = "아메리카노")
    @Pattern(regexp = "^[가-힣a-zA-Z]+$", message = "상품명은 한글, 영어 대소문자만 입력해야 합니다.")
    @Size(min = 1, max = 10, message = "상품명은 1~10 자리로 입력해야 합니다.")
    @NotBlank(message = "상품명은 필수로 입력해야 합니다.")
    private String name;

    @Schema(description = "상품 설명", example = "대표 음료 입니다.")
    @Pattern(regexp = "^[가-힣a-zA-Z0-9]+$", message = "상품설명은 한글, 영어 대소문자, 숫자만 입력해야 합니다.")
    @Size(min = 1, max = 30, message = "상품설명은 1~30 자리로 입력해야 합니다.")
    @NotBlank(message = "상품설명은 필수로 입력해야 합니다.")
    private String description;

    @Schema(description = "상품 가격", example = "2800")
    @Pattern(regexp = "^[0-9]+$", message = "상품 가격은 숫자만 입력 가능합니다.")
    @Size(min = 1, max = 10, message = "상품가격은 1~10 자리로 입력해야 합니다.")
    @NotBlank(message = "상품가격은 필수로 입력해야 합니다.")
    private Integer price;

    @Schema(description = "품절여부", example = "SOLD_OUT")
    @Pattern(regexp = "^[a-zA-Z\\\\p{Punct}]+$", message = "품절여부는 영어, 특수문자만 입력 가능합니다")
    @Size(min = 1, max = 10, message = "품절여부는 1~10 자리로 입력해야 합니다.")
    @NotBlank(message = "품절여부는 필수로 입력해야 합니다.")
    private SoldOutStatus soldOutStatus;

    private String picture;

}
