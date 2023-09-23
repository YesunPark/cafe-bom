package com.zerobase.cafebom.front.review.service;

import static com.zerobase.cafebom.common.exception.ErrorCode.ORDER_PRODUCT_NOT_EXISTS;
import static com.zerobase.cafebom.common.config.security.Role.ROLE_USER;
import static com.zerobase.cafebom.common.type.OrderCookingStatus.COOKING;
import static com.zerobase.cafebom.common.type.Payment.KAKAO_PAY;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

import com.zerobase.cafebom.common.S3UploaderService;
import com.zerobase.cafebom.common.exception.CustomException;
import com.zerobase.cafebom.front.review.service.impl.ReviewService;
import com.zerobase.cafebom.front.member.domain.Member;
import com.zerobase.cafebom.front.member.domain.MemberRepository;
import com.zerobase.cafebom.front.order.domain.Order;
import com.zerobase.cafebom.front.order.domain.OrderRepository;
import com.zerobase.cafebom.front.order.domain.OrderProduct;
import com.zerobase.cafebom.front.order.domain.OrderProductRepository;
import com.zerobase.cafebom.front.review.domain.ReviewRepository;
import com.zerobase.cafebom.front.review.dto.ReviewAddDto;
import com.zerobase.cafebom.front.review.dto.ReviewAddDto.Request;
import com.zerobase.cafebom.common.config.security.TokenProvider;
import java.io.IOException;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

@ExtendWith(MockitoExtension.class)
class ReviewServiceTest {

    @Spy
    @InjectMocks
    private ReviewService reviewService;

    @Mock
    private S3UploaderService s3UploaderService;
    @Mock
    private MemberRepository memberRepository;
    @Mock
    private OrderProductRepository orderProductRepository;
    @Mock
    private OrderRepository orderRepository;
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
        .role(ROLE_USER)
        .build();
    ReviewAddDto.Request request = Request.builder()
        .orderProductId(1L)
        .rating(3)
        .content("test content")
        .build();
    MultipartFile multipartFile;
    OrderProduct orderProduct = OrderProduct.builder()
        .orderId(1L)
        .build();
    Order order = Order.builder()
        .member(member)
        .payment(KAKAO_PAY)
        .cookingStatus(COOKING)
        .build();

    // yesun-23.09.01
    @BeforeEach
    public void setUp() {
        // given
        given(tokenProvider.getId(token)).willReturn(member.getId());
        given(memberRepository.findById(member.getId())).willReturn(Optional.of(member));
    }

    // yesun-23.09.06
    @Test
    @DisplayName("리뷰 생성 성공")
    void successAddReview() throws IOException {
        // given
        given(orderProductRepository.findById(request.getOrderProductId()))
            .willReturn(Optional.of(orderProduct));
        given(orderRepository.findById(orderProduct.getOrderId()))
            .willReturn(Optional.of(order));

        // when
        reviewService.addReview(token, multipartFile, request);

        // then
        then(reviewService).should(times(1)).addReview(token, multipartFile, request);
    }

    // yesun-23.09.04
    @Test
    @DisplayName("리뷰 생성 실패 - 존재하지 않는 주문_상품 ID 요청")
    void successAddReviewOrderProductIdNotExits() throws IOException {
        // given
        given(orderProductRepository.findById(request.getOrderProductId()))
            .willReturn(Optional.empty());

        // when, then
        assertThatThrownBy(() -> reviewService.addReview(token, multipartFile, request))
            .isExactlyInstanceOf(CustomException.class)
            .hasMessage(ORDER_PRODUCT_NOT_EXISTS.getMessage());
        then(reviewService).should(times(1)).addReview(token, multipartFile, request);
    }
}