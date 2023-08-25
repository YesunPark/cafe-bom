package com.zerobase.cafebom.pay.service.dto;

import com.zerobase.cafebom.pay.controller.form.OrdersAddForm;
import com.zerobase.cafebom.pay.controller.form.OrdersAddForm.OrderedProductForm;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OrdersAddDto {

    private String payment; // enum 인데 임시로 스트링

    private List<OrderedProductDto> products;

    public static OrdersAddDto from(OrdersAddForm form) {
        return OrdersAddDto.builder()
            .payment(form.getPayment())
            .products(
                form.getProducts()
                    .stream().map(OrderedProductDto::from)
                    .collect(Collectors.toList())
            )
            .build();
    }

    @Getter
    @Builder
    public static class OrderedProductDto {

        private Integer productId;

        private List<Integer> optionIds;

        public static OrderedProductDto from(OrderedProductForm form) {
            return OrderedProductDto.builder()
                .productId(form.getProductId())
                .optionIds(form.getOptionIds())
                .build();
        }
    }
}