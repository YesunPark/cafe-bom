package com.zerobase.cafebom.front.review.service.impl;

import static com.zerobase.cafebom.common.exception.ErrorCode.MEMBER_NOT_EXISTS;
import static com.zerobase.cafebom.common.exception.ErrorCode.ORDERS_NOT_EXISTS;
import static com.zerobase.cafebom.common.exception.ErrorCode.ORDERS_PRODUCT_NOT_EXISTS;
import static com.zerobase.cafebom.common.exception.ErrorCode.ORDERS_PRODUCT_NOT_MATCH_MEMBER;
import static com.zerobase.cafebom.common.exception.ErrorCode.REVIEW_ALREADY_WRITTEN;

import com.zerobase.cafebom.common.S3UploaderService;
import com.zerobase.cafebom.common.config.security.TokenProvider;
import com.zerobase.cafebom.common.exception.CustomException;
import com.zerobase.cafebom.front.member.domain.Member;
import com.zerobase.cafebom.front.member.domain.MemberRepository;
import com.zerobase.cafebom.front.order.domain.Orders;
import com.zerobase.cafebom.front.order.domain.OrdersProduct;
import com.zerobase.cafebom.front.order.domain.OrdersProductRepository;
import com.zerobase.cafebom.front.order.domain.OrdersRepository;
import com.zerobase.cafebom.front.review.dto.ReviewAddDto.Request;
import com.zerobase.cafebom.front.review.domain.Review;
import com.zerobase.cafebom.front.review.domain.ReviewRepository;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final MemberRepository memberRepository;
    private final OrdersProductRepository ordersProductRepository;
    private final OrdersRepository ordersRepository;
    private final ReviewRepository reviewRepository;

    private final S3UploaderService s3UploaderService;

    private final TokenProvider tokenProvider;

    // 리뷰 등록-yesun-23.09.06
    public void addReview(String token, MultipartFile image, Request request)
        throws IOException {
        Long memberIdByToken = tokenProvider.getId(token);
        Member memberByToken = memberRepository.findById(memberIdByToken)
            .orElseThrow(() -> new CustomException(MEMBER_NOT_EXISTS));

        OrdersProduct ordersProduct = ordersProductRepository.findById(request.getOrdersProductId())
            .orElseThrow(() -> new CustomException(ORDERS_PRODUCT_NOT_EXISTS));

        Orders orders = ordersRepository.findById(ordersProduct.getOrdersId()).orElseThrow(() ->
            new CustomException(ORDERS_NOT_EXISTS)
        );
        if (!memberIdByToken.equals(orders.getMember().getId())) {
            throw new CustomException(ORDERS_PRODUCT_NOT_MATCH_MEMBER);
        }

        reviewRepository.findByOrdersProduct(ordersProduct)
            .ifPresent(review -> {
                throw new CustomException(REVIEW_ALREADY_WRITTEN);
            });

        String s3UploadedUrl = s3UploaderService.uploadFileToS3(image, "dirName");
        reviewRepository.save(Review.from(memberByToken, ordersProduct, request, s3UploadedUrl));
    }
}
