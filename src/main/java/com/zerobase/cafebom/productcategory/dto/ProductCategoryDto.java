package com.zerobase.cafebom.productcategory.dto;

import lombok.Builder;
import lombok.Getter;

public class ProductCategoryDto {

    @Getter
    @Builder
    public static class Response {

        private String name;

        public static ProductCategoryDto.Response from(ProductCategoryForm.Response form) {
            return Response.builder()
                .name(form.getName())
                .build();
        }
    }

    @Getter
    @Builder
    public static class Request {

        private String name;

        public static ProductCategoryDto.Request from(ProductCategoryForm.Request form) {
            return Request.builder()
                .name(form.getName())
                .build();
        }
    }
}
