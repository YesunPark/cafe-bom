package com.zerobase.cafebom.front.review.domain;

import com.zerobase.cafebom.common.BaseTimeEntity;
import com.zerobase.cafebom.front.member.domain.Member;
import com.zerobase.cafebom.front.order.domain.OrderProduct;
import com.zerobase.cafebom.front.review.dto.ReviewAddDto.Request;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
public class Review extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Long memberId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_product_id")
    private OrderProduct orderProduct;

    @NotNull
    private Integer rating;
    // ALTER TABLE review
    // ADD CONSTRAINT check_rating_range CHECK (rating >= 1 AND rating <= 5);
    // entity validation 말고 DB 제약조건은 DB에 직접 설정해주기!

    private String content;

    private String picture;

    public static Review from(Member memberByToken, OrderProduct orderProduct,
        Request request, String s3UploadedUrl) {
        return Review.builder()
            .memberId(memberByToken.getId())
            .orderProduct(orderProduct)
            .rating(request.getRating())
            .content(request.getContent())
            .picture(s3UploadedUrl)
            .build();
    }
}
