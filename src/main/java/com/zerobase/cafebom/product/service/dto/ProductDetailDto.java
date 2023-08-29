package com.zerobase.cafebom.product.service.dto;

import com.zerobase.cafebom.option.domain.entity.Option;
import com.zerobase.cafebom.product.domain.entity.Product;
import com.zerobase.cafebom.product.domain.type.SoldOutStatus;
import com.zerobase.cafebom.productoptioncategory.domain.entity.ProductOptionCategory;
import java.util.List;
import java.util.Map;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@NotNull
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



    public static ProductDetailDto from(Product product, Map<ProductOptionCategory, List<Option>> productOptionList) {
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
