package com.zerobase.cafebom.productcategory.service.dto;

import com.zerobase.cafebom.productcategory.domain.entity.ProductCategory;
import lombok.Builder;
import lombok.Getter;

public class ProductCategoryDto {

    @Getter
    @Builder
    public static class Response {

        private Integer id;

        private String name;

        public static ProductCategoryDto.Response from(ProductCategory productCategory) {
            return Response.builder()
                    .id(productCategory.getId())
                    .name(productCategory.getName())
                    .build();
        }

    }
}
