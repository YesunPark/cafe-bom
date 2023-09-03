package com.zerobase.cafebom.review.service;

import static com.zerobase.cafebom.exception.ErrorCode.MEMBER_NOT_EXISTS;
import static com.zerobase.cafebom.exception.ErrorCode.MEMBER_NOT_PERMITTED;
import static com.zerobase.cafebom.exception.ErrorCode.ORDERS_PRODUCT_NOT_EXISTS;
import static com.zerobase.cafebom.exception.ErrorCode.REVIEW_NOT_EXISTS;

import com.zerobase.cafebom.exception.CustomException;
import com.zerobase.cafebom.member.domain.entity.Member;
import com.zerobase.cafebom.member.repository.MemberRepository;
import com.zerobase.cafebom.ordersproduct.domain.entity.OrdersProduct;
import com.zerobase.cafebom.ordersproduct.repository.OrdersProductRepository;
import com.zerobase.cafebom.review.domain.entity.Review;
import com.zerobase.cafebom.review.repository.ReviewRepository;
import com.zerobase.cafebom.review.service.dto.ReviewAddDto;
import com.zerobase.cafebom.security.TokenProvider;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final MemberRepository memberRepository;
    private final OrdersProductRepository ordersProductRepository;
    private final ReviewRepository reviewRepository;

    private final TokenProvider tokenProvider;

    // 리뷰 생성-yesun-23.09.01
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

        // 리뷰 사진 S3 에 저장되는 부분은 지연님과 얘기해서 추가해야 함
    }

    // 리뷰 삭제-yesun-23.09.03
    public void removeReview(String token, long reviewId) {
        Long userIdByToken = tokenProvider.getId(token);
        Review reviewByRequest = reviewRepository.findById(reviewId)
            .orElseThrow(() -> new CustomException(REVIEW_NOT_EXISTS));

        if (!Objects.equals(userIdByToken, reviewByRequest.getMemberId())) {
            throw new CustomException(MEMBER_NOT_PERMITTED);
        }

        reviewRepository.delete(reviewByRequest);
    }
}
