package com.zerobase.cafebom.cart.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public class CartListForm {

    @AllArgsConstructor
    @Builder
    @Getter
    public static class Response {

        private Long cartId;

        private Integer productId;

        private String productName;

        private String productPicture;

        private List<CartListOptionDto> cartListOptionDtos;

        private Integer productCount;

        public static CartListForm.Response from(CartListDto cartListDto) {
            return Response.builder()
                .cartId(cartListDto.getCartId())
                .productId(cartListDto.getProductId())
                .productName(cartListDto.getProductName())
                .productPicture(cartListDto.getProductPicture())
                .cartListOptionDtos(cartListDto.getCartListOptionDtos())
                .productCount(cartListDto.getProductCount())
                .build();
        }
    }
}
