package com.zerobase.cafebom.orders.controller.form;

import com.zerobase.cafebom.orders.domain.type.Payment;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OrdersAddForm {

    @Schema(description = "결제 수단", example = "KAKAO_PAY")
    @NotNull(message = "결제 수단 필수로 입력해야 합니다.")
    private Payment payment;

    @NotNull(message = "결제 수단은 필수로 입력해야 합니다.")
    private List<OrderedProductForm> products;

    @Getter
    @Builder
    public static class OrderedProductForm {

        @Schema(description = "상품 ID", example = "1")
        @NotBlank(message = "상품 ID는 필수로 입력해야 합니다.")
        @Min(value = 1, message = "상품 ID는 1부터 조회할 수 있습니다.")
        private Integer productId;

        @Schema(description = "옵션 ID 목록", example = "[1, 2, 3]")
        @NotNull(message = "옵션 ID 목록은 필수로 입력해야 합니다.")
        private List<Integer> optionIds;
    }
}