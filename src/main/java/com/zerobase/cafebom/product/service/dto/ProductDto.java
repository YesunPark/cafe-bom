package com.zerobase.cafebom.product.service.dto;


import com.zerobase.cafebom.product.controller.form.ProductForm;
import com.zerobase.cafebom.product.domain.entity.SoldOutStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductDto {

    private Integer id;

    private Integer productCategory;

    private String name;

    private String description;

    private Integer price;

    private SoldOutStatus soldOutStatus;

    private String picture;

    public static ProductDto from(ProductForm productForm){
        return ProductDto.builder()
                .name(productForm.getName())
                .productCategory(productForm.getProductCategoryId())
                .description(productForm.getDescription())
                .price(productForm.getPrice())
                .soldOutStatus(productForm.getSoldOutStatus())
                .picture(productForm.getPicture())
                .build();
    }

}
