package com.zerobase.cafebom.product.controller.form;

import com.zerobase.cafebom.option.domain.entity.Option;
import com.zerobase.cafebom.product.domain.type.SoldOutStatus;
import com.zerobase.cafebom.product.service.dto.ProductDetailDto;
import com.zerobase.cafebom.productoptioncategory.domain.entity.ProductOptionCategory;
import java.util.List;
import java.util.Map;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public class ProductDetailForm {

    @AllArgsConstructor
    @NotNull
    @Builder
    @Getter
    public static class Response {

        private Integer productId;

        private String name;

        private String description;

        private Integer price;

        private SoldOutStatus soldOutStatus;

        private String picture;

        private Map<ProductOptionCategory, List<Option>> productOptionList;

        public static ProductDetailForm.Response from(
            ProductDetailDto productDetailDto) {
            return ProductDetailForm.Response.builder()
                .productId(productDetailDto.getProductId())
                .name(productDetailDto.getName())
                .description(productDetailDto.getDescription())
                .price(productDetailDto.getPrice())
                .soldOutStatus(productDetailDto.getSoldOutStatus())
                .picture(productDetailDto.getPicture())
                .productOptionList(productDetailDto.getProductOptionList())
                .build();
        }
    }
}
