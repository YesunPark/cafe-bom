package com.zerobase.cafebom.productcategory.controller.form;

import com.zerobase.cafebom.productcategory.service.dto.ProductCategoryDto;
import lombok.Builder;
import lombok.Getter;

public class ProductCategoryForm {

    @Getter
    @Builder
    public static class Response{

        private Integer id;

        private String name;

        public static ProductCategoryForm.Response from(ProductCategoryDto.Response productCategoryDto) {
            return Response.builder()
                    .id(productCategoryDto.getId())
                    .name(productCategoryDto.getName())
                    .build();
        }
    }
}
