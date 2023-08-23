package com.zerobase.cafebom.product.service.dto;

import com.zerobase.cafebom.product.domain.entity.Product;
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

    private Byte[] picture;

    public static ProductDto from(Product product) {
        return ProductDto.builder()
                .productId(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .picture(product.getPicture())
                .build();
    }
}
