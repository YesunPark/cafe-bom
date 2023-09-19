package com.zerobase.cafebom.product.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public class ProductListForm {

    @AllArgsConstructor
    @Builder
    @Getter
    public static class Response {

        private List<ProductDto> productDtoList;
    }
}
