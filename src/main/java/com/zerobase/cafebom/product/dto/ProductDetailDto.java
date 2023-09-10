package com.zerobase.cafebom.product.dto;

import com.zerobase.cafebom.option.domain.Option;
import com.zerobase.cafebom.product.domain.Product;
import com.zerobase.cafebom.productoptioncategory.domain.ProductOptionCategory;
import com.zerobase.cafebom.type.SoldOutStatus;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class ProductDetailDto {

    private Integer productId;

    private String name;

    private String description;

    private Integer price;

    private SoldOutStatus soldOutStatus;

    private String picture;

    private Map<ProductOptionCategory, List<Option>> productOptionList;


    public static ProductDetailDto from(Product product,
        Map<ProductOptionCategory, List<Option>> productOptionList) {
        return ProductDetailDto.builder()
            .productId(product.getId())
            .name(product.getName())
            .description(product.getDescription())
            .price(product.getPrice())
            .soldOutStatus(product.getSoldOutStatus())
            .picture(product.getPicture())
            .productOptionList(productOptionList)
            .build();
    }
}
