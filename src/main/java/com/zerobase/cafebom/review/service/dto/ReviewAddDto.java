package com.zerobase.cafebom.review.service.dto;

import com.zerobase.cafebom.review.controller.form.ReviewAddForm;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class ReviewAddDto {

    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Getter
    public static class Request {

        private long ordersProductId;

        private int rating;

        private String content;

        private String picture;

        public static ReviewAddDto.Request from(ReviewAddForm.Request request) {
            return Request.builder()
                .ordersProductId(request.getOrdersProductId())
                .rating(request.getRating())
                .content(request.getContent())
                .picture(request.getPicture())
                .build();
        }
    }
}
