package com.zerobase.cafebom.admin.dto;

import com.zerobase.cafebom.product.domain.Product;
import com.zerobase.cafebom.type.SoldOutStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AdminProductForm {

    @NotNull
    private Integer productCategoryId;

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
    @Size(min = 1, max = 10, message = "품절여부는 1~10 자리로 입력해야 합니다.")
    @NotNull(message = "품절여부는 필수로 입력해야 합니다.")
    private SoldOutStatus soldOutStatus;

    private String picture;

    @Getter
    @Builder
    public static class Response {

        private Integer id;

        private Integer productCategoryId;

        private String name;

        private String description;

        private Integer price;

        private SoldOutStatus soldOutStatus;

        private String picture;

        public static Response from(Product product) {
            return Response.builder()
                .id(product.getId())
                .productCategoryId(product.getProductCategory().getId())
                .name(product.getName())
                .description(product.getDescription())
                .soldOutStatus(product.getSoldOutStatus())
                .picture(product.getPicture())
                .build();
        }
    }
}
