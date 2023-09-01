package com.zerobase.cafebom.review.service;

import com.zerobase.cafebom.review.repository.ReviewRepository;
import com.zerobase.cafebom.review.service.dto.ReviewAddDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    // 리뷰 저장-yesun-23.09.01
    public void addReview(String token, ReviewAddDto.Request dto) {

    }
}
