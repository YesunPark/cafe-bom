package com.zerobase.cafebom.review.controller;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

import com.zerobase.cafebom.review.controller.form.ReviewAddForm;
import com.zerobase.cafebom.review.service.ReviewService;
import com.zerobase.cafebom.review.service.dto.ReviewAddDto;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.io.IOException;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "review-controller", description = "리뷰 관련 API")
@RestController
@RequestMapping("/auth/review")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    // yesun-23.09.04
    @ApiOperation(value = "리뷰 생성", notes = "토큰, 주문상품 정보, 별점, 내용, 사진을 받아 리뷰를 생성합니다.")
    @PostMapping(consumes = MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> reviewAdd(
        @RequestHeader(name = "Authorization") String token,
        @RequestParam(value = "image") MultipartFile image,
        @Valid ReviewAddForm.Request reviewAddForm) throws IOException {
        reviewService.addReview(token, image, ReviewAddDto.Request.from(reviewAddForm));
        return ResponseEntity.status(CREATED).build();
    }
}