package com.zerobase.cafebom.cart.dto;

import com.zerobase.cafebom.option.domain.Option;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public class CartListForm {

    @AllArgsConstructor
    @Builder
    @Getter
    public static class Response {

        private Integer productId;

        private String productName;

        private String productPicture;

        private List<Option> productOptions;

        private Integer productCount;

        public static CartListForm.Response from(CartListDto cartListDto) {
            return CartListForm.Response.builder()
                .productId(cartListDto.getProductId())
                .productName(cartListDto.getProductName())
                .productPicture(cartListDto.getProductPicture())
                .productOptions(cartListDto.getProductOptions())
                .productCount(cartListDto.getProductCount())
                .build();
        }
    }
}
