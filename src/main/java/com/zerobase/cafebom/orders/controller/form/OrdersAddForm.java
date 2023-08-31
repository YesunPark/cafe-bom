package com.zerobase.cafebom.orders.controller.form;

import com.zerobase.cafebom.orders.domain.type.Payment;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

public class OrdersAddForm {

    @Getter
    @Builder
    public static class Request {

        @Schema(description = "결제 수단", example = "KAKAO_PAY")
        @NotNull(message = "결제 수단은 필수로 입력해야 합니다.")
        private Payment payment;
    }
}