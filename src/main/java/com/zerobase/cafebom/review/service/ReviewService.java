package com.zerobase.cafebom.review.service;

import static com.zerobase.cafebom.exception.ErrorCode.REVIEW_ALREADY_WRITTEN;
import static com.zerobase.cafebom.exception.ErrorCode.MEMBER_NOT_EXISTS;
import static com.zerobase.cafebom.exception.ErrorCode.ORDERS_PRODUCT_NOT_EXISTS;

import com.zerobase.cafebom.admin.service.S3UploaderService;
import com.zerobase.cafebom.exception.CustomException;
import com.zerobase.cafebom.member.domain.entity.Member;
import com.zerobase.cafebom.member.repository.MemberRepository;
import com.zerobase.cafebom.ordersproduct.domain.entity.OrdersProduct;
import com.zerobase.cafebom.ordersproduct.repository.OrdersProductRepository;
import com.zerobase.cafebom.review.domain.entity.Review;
import com.zerobase.cafebom.review.repository.ReviewRepository;
import com.zerobase.cafebom.review.service.dto.ReviewAddDto;
import com.zerobase.cafebom.security.TokenProvider;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final MemberRepository memberRepository;
    private final OrdersProductRepository ordersProductRepository;
    private final ReviewRepository reviewRepository;

    private final S3UploaderService s3UploaderService;

    private final TokenProvider tokenProvider;

    // 리뷰 생성-yesun-23.09.04
    public void addReview(String token, MultipartFile image, ReviewAddDto.Request request)
        throws IOException {
        Long memberIdByToken = tokenProvider.getId(token);
        Member memberByToken = memberRepository.findById(memberIdByToken)
            .orElseThrow(() -> new CustomException(MEMBER_NOT_EXISTS));

        OrdersProduct ordersProduct = ordersProductRepository.findById(request.getOrdersProductId())
            .orElseThrow(() -> new CustomException(ORDERS_PRODUCT_NOT_EXISTS));

        reviewRepository.findByOrdersProduct(ordersProduct)
            .ifPresent(review -> {
                throw new CustomException(REVIEW_ALREADY_WRITTEN);
            });

        String s3UploadedUrl = s3UploaderService.uploadFileToS3(image, "dirName");
        reviewRepository.save(Review.from(memberByToken, ordersProduct, request, s3UploadedUrl));
    }
}
