package com.zerobase.cafebom.orders.service.dto;

import com.zerobase.cafebom.orders.domain.type.Payment;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class OrdersAddDto {

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request {

        private Payment payment;

        private List<ProductOrderedDto> products;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProductOrderedDto {

        private Integer productId;

        private Integer count;

        private List<Integer> optionIds;
    }
}