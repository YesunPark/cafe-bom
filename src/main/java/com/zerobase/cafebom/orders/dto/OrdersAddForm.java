package com.zerobase.cafebom.orders.dto;

import com.zerobase.cafebom.type.Payment;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class OrdersAddForm {

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Request {

        @Schema(description = "결제 수단", example = "KAKAO_PAY")
        @NotNull(message = "결제 수단은 필수로 입력해야 합니다.")
        private Payment payment;
    }
}