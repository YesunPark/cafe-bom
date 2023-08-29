package com.zerobase.cafebom.product.service.dto;

import com.zerobase.cafebom.product.domain.entity.Product;
import com.zerobase.cafebom.product.domain.entity.SoldOutStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NotNull
@Builder
@Getter
public class ProductDto {

    private Integer productId;

    private String name;

    private Integer price;

    private SoldOutStatus soldOutStatus;

    private String picture;

    public static ProductDto from(Product product) {
        return ProductDto.builder()
            .productId(product.getId())
            .name(product.getName())
            .price(product.getPrice())
            .soldOutStatus(product.getSoldOutStatus())
            .picture(product.getPicture())
            .build();
    }
}
