package com.zerobase.cafebom.pay.controller.form;

import com.zerobase.cafebom.pay.service.dto.AddOrdersDto.OrderedProductDto;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class AddOrdersForm {

    @Schema(description = "결제 수단", example = "KAKAO_PAY")
    private String payment; // enum 인데 임시로 스트링

//    @Schema(description = "주문 상품들",
//        example =)
//    @ApiModelProperty({
//        description: 'product no',
//        example: 'PN43443223',
//        })
    private List<OrderedProductForm> products;

    @Getter
    public static class OrderedProductForm {

        private Integer productId;

        private List<Integer> optionIds;
    }
}