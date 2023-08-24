package com.zerobase.cafebom.pay.controller.form;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class PayOrdersForm {

    @Schema(description = "결제 수단", example = "KAKAO_PAY")
    private String payment; // enum 인데 임시로 스트링

    @Schema(description = "주문 상품들",
        example = "["
            + "{productId : 1,"
            + "optionIds : [1,2,3]}, "
            + "{productId : 1,"
            + "optionIds : [1,4,5]}, "
            + "{productId : 2,"
            + "optionIds : [2,5]}"
            + "]")
    private List<OrderedProductForm> products;

    @Getter
    public static class OrderedProductForm {

        private Integer productId;

        private List<Integer> optionIds;
    }
}