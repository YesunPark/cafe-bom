package com.zerobase.cafebom.product.controller.form;

import com.zerobase.cafebom.product.service.dto.ProductDto;
import lombok.AllArgsConstructor;
import lombok.Builder;

import javax.validation.constraints.NotNull;
import lombok.Getter;

public class ProductListForm {

    @AllArgsConstructor
    @NotNull
    @Builder
    @Getter
    public static class Response {

        private Integer productId;

        private String name;

        private Integer price;

        private Byte[] picture;

        public static ProductListForm.Response from(ProductDto productDto) {
            return ProductListForm.Response.builder()
                    .productId(productDto.getProductId())
                    .name(productDto.getName())
                    .price(productDto.getPrice())
                    .picture(productDto.getPicture())
                    .build();
        }
    }
}
