package com.zerobase.cafebom.pay.service.dto;

import com.zerobase.cafebom.pay.controller.form.AddOrdersForm;
import com.zerobase.cafebom.pay.controller.form.AddOrdersForm.OrderedProductForm;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AddOrdersDto {

    private String payment; // enum 인데 임시로 스트링

    private List<OrderedProductDto> products;

    public static AddOrdersDto from(AddOrdersForm form) {
        return AddOrdersDto.builder()
            .payment(form.getPayment())
            .products(
                form.getProducts()
                    .stream().map(OrderedProductDto::from)
                    .collect(Collectors.toList())
            )
            .build();
    }
}

@Getter
@Builder
class OrderedProductDto {

    private Integer productId;

    private List<Integer> optionIds;

    public static OrderedProductDto from(OrderedProductForm form) {
        return OrderedProductDto.builder()
            .productId(form.getProductId())
            .optionIds(form.getOptionIds())
            .build();
    }
}