package com.zerobase.cafebom.admin.service.dto;


import com.zerobase.cafebom.admin.controller.form.AdminProductForm;
import com.zerobase.cafebom.product.domain.type.SoldOutStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AdminProductDto {

    private Integer id;

    private Integer productCategoryId;

    private String name;

    private String description;

    private Integer price;

    private SoldOutStatus soldOutStatus;

    private String picture;

    public static AdminProductDto from(AdminProductForm productForm) {
        return AdminProductDto.builder()
            .name(productForm.getName())
            .productCategoryId(productForm.getProductCategoryId())
            .description(productForm.getDescription())
            .price(productForm.getPrice())
            .soldOutStatus(productForm.getSoldOutStatus())
            .picture(productForm.getPicture())
            .build();
    }
}
