package com.zerobase.cafebom.product.service.dto;


import com.zerobase.cafebom.product.domain.entity.Product;
import com.zerobase.cafebom.product.domain.type.SoldOutStatus;
import com.zerobase.cafebom.productcategory.domain.entity.ProductCategory;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductDto {

    private Integer id;

    private ProductCategory productCategory;

    private String name;

    private String description;

    private Integer price;

    private SoldOutStatus soldOutStatus;

    private String picture;

    public static ProductDto from(Product product){
        return ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .soldOutStatus(product.getSoldOutStatus())
                .picture(product.getPicture())
                .build();
    }

}
