package com.zerobase.cafebom.review.service;

import static com.zerobase.cafebom.exception.ErrorCode.MEMBER_NOT_EXISTS;
import static com.zerobase.cafebom.exception.ErrorCode.ORDERS_PRODUCT_NOT_EXISTS;

import com.zerobase.cafebom.exception.CustomException;
import com.zerobase.cafebom.member.domain.entity.Member;
import com.zerobase.cafebom.member.repository.MemberRepository;
import com.zerobase.cafebom.ordersproduct.domain.entity.OrdersProduct;
import com.zerobase.cafebom.ordersproduct.repository.OrdersProductRepository;
import com.zerobase.cafebom.review.domain.entity.Review;
import com.zerobase.cafebom.review.repository.ReviewRepository;
import com.zerobase.cafebom.review.service.dto.ReviewAddDto;
import com.zerobase.cafebom.security.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final MemberRepository memberRepository;
    private final OrdersProductRepository ordersProductRepository;
    private final ReviewRepository reviewRepository;

    private final TokenProvider tokenProvider;

    // 리뷰 저장-yesun-23.09.01
    public void addReview(String token, ReviewAddDto.Request request) {
        Long userId = tokenProvider.getId(token);
        Member memberById = memberRepository.findById(userId)
            .orElseThrow(() -> new CustomException(MEMBER_NOT_EXISTS));

        OrdersProduct ordersProduct = ordersProductRepository.findById(request.getOrdersProductId())
            .orElseThrow(() -> new CustomException(ORDERS_PRODUCT_NOT_EXISTS));

        reviewRepository.save(Review.builder()
            .memberId(memberById.getId())
            .ordersProduct(ordersProduct)
            .rating(request.getRating())
            .content(request.getContent())
            .picture(request.getPicture())
            .build());
    }
}
