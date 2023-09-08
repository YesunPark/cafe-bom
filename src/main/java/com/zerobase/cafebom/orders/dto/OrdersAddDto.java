package com.zerobase.cafebom.orders.dto;

import com.zerobase.cafebom.type.Payment;
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

        public static OrdersAddDto.Request from(OrdersAddForm.Request form) {
            return Request.builder()
                .payment(form.getPayment())
                .build();
        }
    }
}