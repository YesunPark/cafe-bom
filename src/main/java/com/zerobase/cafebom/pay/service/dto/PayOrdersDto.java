package com.zerobase.cafebom.pay.service.dto;

import com.zerobase.cafebom.pay.controller.form.PayOrdersForm;
import com.zerobase.cafebom.pay.controller.form.PayOrdersForm.OrderedProductForm;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Getter;

//@AllArgsConstructor
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
public class PayOrdersDto {

    private String payment; // enum 인데 임시로 스트링

    private List<OrderedProductDto> products;

    public static PayOrdersDto from(PayOrdersForm form, String payment) {
        return PayOrdersDto.builder()
            .payment(payment)
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