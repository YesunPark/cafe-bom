package com.zerobase.cafebom.product.dto;

import com.zerobase.cafebom.type.SoldOutStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public class ProductListForm {

    @AllArgsConstructor
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
