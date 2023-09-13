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

        private List<CartListDto> cartListDtoList;

    }
}
