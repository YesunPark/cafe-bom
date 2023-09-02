package com.zerobase.cafebom.productcategory.service.dto;

import com.zerobase.cafebom.productcategory.controller.form.ProductCategoryForm;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
//main
public class ProductCategoryDto {

    private String name;

    public static ProductCategoryDto from(ProductCategoryForm.Response form) {
        return ProductCategoryDto.builder()
                .name(form.getName())
                .build();
    }
    //modify
public class ProductCategoryDto {

    @Getter
    @Builder
    public static class Request{
        private String name;

        public static ProductCategoryDto.Request from(ProductCategoryForm.Request form) {
            return Request.builder()
                    .name(form.getName())
                    .build();
        }
    }

}
