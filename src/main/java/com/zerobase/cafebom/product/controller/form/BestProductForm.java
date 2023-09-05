package com.zerobase.cafebom.product.controller.form;

import com.zerobase.cafebom.type.SoldOutStatus;
import com.zerobase.cafebom.product.service.dto.BestProductDto;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public class BestProductForm {

    @AllArgsConstructor
    @NotNull
    @Builder
    @Getter
    public static class Response {

        private Integer productId;

        private String name;

        private Integer price;

        private SoldOutStatus soldOutStatus;

        private String picture;

        public static List<BestProductForm.Response> from(List<BestProductDto> bestProductDto) {
            return bestProductDto.stream()
                .map(productDto -> BestProductForm.Response.builder()
                    .productId(productDto.getProductId())
                    .name(productDto.getName())
                    .price(productDto.getPrice())
                    .soldOutStatus(productDto.getSoldOutStatus())
                    .picture(productDto.getPicture())
                    .build())
                .collect(Collectors.toList());
        }
    }
}
