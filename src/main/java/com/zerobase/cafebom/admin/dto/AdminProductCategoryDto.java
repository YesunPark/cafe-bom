package com.zerobase.cafebom.admin.dto;

import com.zerobase.cafebom.productcategory.domain.ProductCategory;
import lombok.Builder;
import lombok.Getter;

public class AdminProductCategoryDto {

    @Getter
    @Builder
    public static class Response {

        private Integer productCategoryId;

        private String name;

        public static AdminProductCategoryDto.Response from(ProductCategory productCategory) {
            return Response.builder()
                    .productCategoryId(productCategory.getId())
                    .name(productCategory.getName())
                    .build();
        }
    }

    @Getter
    @Builder
    public static class Request {

        private String name;

        public static AdminProductCategoryDto.Request from(AdminProductCategoryForm.Request form) {
            return Request.builder()
                    .name(form.getName())
                    .build();
        }
    }
}
