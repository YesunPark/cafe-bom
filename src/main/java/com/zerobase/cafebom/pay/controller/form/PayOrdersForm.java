package com.zerobase.cafebom.pay.controller.form;

import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class PayOrdersForm {

    private String payment; // enum 인데 임시로 스트링

    private List<OrderedProductForm> products;

    @Getter
    public static class OrderedProductForm {

        private Integer productId;

        private List<Integer> optionIds;
    }
}