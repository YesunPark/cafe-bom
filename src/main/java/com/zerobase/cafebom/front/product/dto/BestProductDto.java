package com.zerobase.cafebom.front.product.dto;

import com.zerobase.cafebom.front.product.domain.Product;
import com.zerobase.cafebom.common.type.SoldOutStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BestProductDto {

    private Integer productId;

    private String name;

    private Integer price;

    private SoldOutStatus soldOutStatus;

    private String picture;

    public static BestProductDto from(Product product) {
        return BestProductDto.builder()
            .productId(product.getId())
            .name(product.getName())
            .price(product.getPrice())
            .soldOutStatus(product.getSoldOutStatus())
            .picture(product.getPicture())
            .build();
    }
}
