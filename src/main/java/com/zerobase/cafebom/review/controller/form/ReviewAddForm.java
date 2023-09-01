package com.zerobase.cafebom.review.controller.form;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

public class ReviewAddForm {

    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Getter
    public static class Request {

        @NotNull(message = "주문_상품 ID는 필수로 입력해야 합니다.")
        @Min(value = 1, message = "주문_상품 ID는 1부터 입력할 수 있습니다.")
        private long ordersProductId;

        @NotNull
        @Range(min = 1, max = 5, message = "별점은 1~5점 사이로 입력해야 합니다.")
        private int rating;

        private String content;

        private String picture;
    }
}
