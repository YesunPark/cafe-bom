package com.zerobase.cafebom.front.review.dto;

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

        private long orderProductId;

        private int rating;

        private String content;

        public static ReviewAddDto.Request from(ReviewAddForm.Request request) {
            return Request.builder()
                .orderProductId(request.getOrderProductId())
                .rating(request.getRating())
                .content(request.getContent())
                .build();
        }
    }
}
