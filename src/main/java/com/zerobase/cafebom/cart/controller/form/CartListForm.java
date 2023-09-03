package com.zerobase.cafebom.cart.controller.form;

import com.zerobase.cafebom.cart.service.dto.CartListDto;
import com.zerobase.cafebom.option.domain.entity.Option;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.util.List;

public class CartListForm {

    @AllArgsConstructor
    @NotNull
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
