package com.zerobase.cafebom.review.service;

import static com.zerobase.cafebom.exception.ErrorCode.ORDERS_PRODUCT_NOT_EXISTS;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

import com.zerobase.cafebom.exception.CustomException;
import com.zerobase.cafebom.member.domain.entity.Member;
import com.zerobase.cafebom.member.repository.MemberRepository;
import com.zerobase.cafebom.ordersproduct.domain.entity.OrdersProduct;
import com.zerobase.cafebom.ordersproduct.repository.OrdersProductRepository;
import com.zerobase.cafebom.review.repository.ReviewRepository;
import com.zerobase.cafebom.review.service.dto.ReviewAddDto;
import com.zerobase.cafebom.review.service.dto.ReviewAddDto.Request;
import com.zerobase.cafebom.security.Role;
import com.zerobase.cafebom.security.TokenProvider;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ReviewServiceTest {

    @Spy
    @InjectMocks
    private ReviewService reviewService;

    @Mock
    private MemberRepository memberRepository;
    @Mock
    private OrdersProductRepository ordersProductRepository;
    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private TokenProvider tokenProvider;

    String token = "Bearer token";
    Member member = Member.builder()
        .id(1L)
        .password("password")
        .nickname("testNickname")
        .phone("01022223333")
        .email("test@naber.com")
        .role(Role.ROLE_USER)
        .build();
    ReviewAddDto.Request request = Request.builder()
        .ordersProductId(1L)
        .rating(3)
        .content("test content")
        .picture("test picture")
        .build();
    OrdersProduct ordersProduct = OrdersProduct.builder().build();

    // yesun-23.09.01
    @BeforeEach
    public void setUp() {
        // given
        given(tokenProvider.getId(token)).willReturn(member.getId());
        given(memberRepository.findById(member.getId())).willReturn(Optional.of(member));
    }

    // yesun-23.09.01
    @Test
    @DisplayName("리뷰 저장 성공")
    void successAddReview() {
        // given
        given(ordersProductRepository.findById(request.getOrdersProductId()))
            .willReturn(Optional.of(ordersProduct));

        // when
        reviewService.addReview(token, request);

        // then
        then(reviewService).should(times(1)).addReview(token, request);
    }

    // yesun-23.09.01
    @Test
    @DisplayName("리뷰 저장 실패 - 존재하지 않는 주문_상품 ID 요청")
    void successAddReviewOrdersProductIdNotExits() {
        // given
        given(ordersProductRepository.findById(request.getOrdersProductId()))
            .willReturn(Optional.empty());

        // when, then
        assertThatThrownBy(() -> reviewService.addReview(token, request))
            .isExactlyInstanceOf(CustomException.class)
            .hasMessage(ORDERS_PRODUCT_NOT_EXISTS.getMessage());
        then(reviewService).should(times(1)).addReview(token, request);
    }
}