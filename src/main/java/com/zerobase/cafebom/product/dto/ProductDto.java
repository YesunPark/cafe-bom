package com.zerobase.cafebom.product.dto;

import com.zerobase.cafebom.product.domain.Product;
import com.zerobase.cafebom.type.SoldOutStatus;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

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
