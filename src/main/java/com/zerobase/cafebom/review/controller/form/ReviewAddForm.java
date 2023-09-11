package com.zerobase.cafebom.review.controller.form;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.Min;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.validator.constraints.Range;

public class ReviewAddForm {

    @Builder
    @Getter
    public static class Request {

        @Schema(description = "주문 상품 ID", example = "1")
        @Min(value = 1, message = "주문 상품 ID는 1부터 입력해야 합니다.")
        private long ordersProductId;

        @Schema(description = "별점", example = "5")
        @Range(min = 1, max = 5, message = "별점은 1~5점 사이로 입력해야 합니다.")
        private int rating;

        @Schema(description = "내용", example = "역시 갈증날 땐 아아가 최고에요!!")
        private String content;
    }
}