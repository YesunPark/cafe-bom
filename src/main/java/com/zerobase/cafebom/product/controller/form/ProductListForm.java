package com.zerobase.cafebom.product.controller.form;

import com.zerobase.cafebom.product.domain.type.SoldOutStatus;
import com.zerobase.cafebom.product.service.dto.ProductDto;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
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

        private SoldOutStatus soldOutStatus;

        private String picture;

        public static ProductListForm.Response from(ProductDto productDto) {
            return ProductListForm.Response.builder()
                .productId(productDto.getProductId())
                .name(productDto.getName())
                .price(productDto.getPrice())
                .soldOutStatus(productDto.getSoldOutStatus())
                .picture(productDto.getPicture())
                .build();
        }
    }
}
