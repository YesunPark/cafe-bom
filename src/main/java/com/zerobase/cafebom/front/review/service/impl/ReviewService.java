package com.zerobase.cafebom.front.review.service.impl;

import static com.zerobase.cafebom.common.exception.ErrorCode.MEMBER_NOT_EXISTS;
import static com.zerobase.cafebom.common.exception.ErrorCode.ORDER_NOT_EXISTS;
import static com.zerobase.cafebom.common.exception.ErrorCode.ORDER_PRODUCT_NOT_EXISTS;
import static com.zerobase.cafebom.common.exception.ErrorCode.ORDER_PRODUCT_NOT_MATCH_MEMBER;
import static com.zerobase.cafebom.common.exception.ErrorCode.REVIEW_ALREADY_WRITTEN;

import com.zerobase.cafebom.common.S3UploaderService;
import com.zerobase.cafebom.common.config.security.TokenProvider;
import com.zerobase.cafebom.common.exception.CustomException;
import com.zerobase.cafebom.front.member.domain.Member;
import com.zerobase.cafebom.front.member.domain.MemberRepository;
import com.zerobase.cafebom.front.order.domain.Order;
import com.zerobase.cafebom.front.order.domain.OrderProduct;
import com.zerobase.cafebom.front.order.domain.OrderProductRepository;
import com.zerobase.cafebom.front.order.domain.OrderRepository;
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
    private final OrderProductRepository orderProductRepository;
    private final OrderRepository orderRepository;
    private final ReviewRepository reviewRepository;

    private final S3UploaderService s3UploaderService;

    private final TokenProvider tokenProvider;

    // 리뷰 등록-yesun-23.09.06
    public void addReview(String token, MultipartFile image, Request request)
        throws IOException {
        Long memberIdByToken = tokenProvider.getId(token);
        Member memberByToken = memberRepository.findById(memberIdByToken)
            .orElseThrow(() -> new CustomException(MEMBER_NOT_EXISTS));

        OrderProduct orderProduct = orderProductRepository.findById(request.getOrderProductId())
            .orElseThrow(() -> new CustomException(ORDER_PRODUCT_NOT_EXISTS));

        Order order = orderRepository.findById(orderProduct.getOrderId()).orElseThrow(() ->
            new CustomException(ORDER_NOT_EXISTS)
        );
        if (!memberIdByToken.equals(order.getMember().getId())) {
            throw new CustomException(ORDER_PRODUCT_NOT_MATCH_MEMBER);
        }

        reviewRepository.findByOrderProduct(orderProduct)
            .ifPresent(review -> {
                throw new CustomException(REVIEW_ALREADY_WRITTEN);
            });

        String s3UploadedUrl = s3UploaderService.uploadFileToS3(image, "dirName");
        reviewRepository.save(Review.from(memberByToken, orderProduct, request, s3UploadedUrl));
    }
}
