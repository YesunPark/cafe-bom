package com.zerobase.cafebom.review.controller;

import com.zerobase.cafebom.review.controller.form.ReviewAddForm;
import com.zerobase.cafebom.review.service.ReviewService;
import com.zerobase.cafebom.review.service.dto.ReviewAddDto;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "review-controller", description = "리뷰 관련 API")
@RestController
@RequestMapping("/auth/review")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    // yesun-23.09.01
    @ApiOperation(value = "리뷰 생성", notes = "토큰, 주문상품 정보, 별점, 내용, 사진을 받아 리뷰를 생성합니다.")
    @PostMapping
    public ResponseEntity<Void> reviewAdd(
        @RequestHeader(name = "Authorization") String token,
        @RequestBody @Valid ReviewAddForm.Request reviewAddForm) {
        reviewService.addReview(token, ReviewAddDto.Request.from(reviewAddForm));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
