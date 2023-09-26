package com.zerobase.cafebom.front.order.service;

import static com.zerobase.cafebom.common.exception.ErrorCode.CART_IS_EMPTY;
import static com.zerobase.cafebom.common.exception.ErrorCode.MEMBER_NOT_EXISTS;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

import com.zerobase.cafebom.front.cart.domain.Cart;
import com.zerobase.cafebom.front.cart.domain.CartRepository;
import com.zerobase.cafebom.front.cart.domain.CartOptionRepository;
import com.zerobase.cafebom.common.exception.CustomException;
import com.zerobase.cafebom.front.order.domain.Order;
import com.zerobase.cafebom.front.order.service.impl.OrderService;
import com.zerobase.cafebom.front.member.domain.Member;
import com.zerobase.cafebom.front.member.domain.MemberRepository;
import com.zerobase.cafebom.front.product.domain.OptionRepository;
import com.zerobase.cafebom.front.order.domain.OrderRepository;
import com.zerobase.cafebom.front.order.dto.OrderAddDto;
import com.zerobase.cafebom.front.order.dto.OrderAddDto.Request;
import com.zerobase.cafebom.front.order.domain.OrderProduct;
import com.zerobase.cafebom.front.order.domain.OrderProductRepository;
import com.zerobase.cafebom.front.order.domain.OrderProductOptionRepository;
import com.zerobase.cafebom.front.product.domain.Product;
import com.zerobase.cafebom.common.config.security.Role;
import com.zerobase.cafebom.common.config.security.TokenProvider;
import com.zerobase.cafebom.common.type.Payment;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@ExtendWith(MockitoExtension.class)
class OrderAddServiceTest {

    @Spy
    @InjectMocks
    private OrderService orderService;

    @Mock
    private MemberRepository memberRepository;
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private CartRepository cartRepository;
    @Mock
    private CartOptionRepository cartOptionRepository;
    @Mock
    private OptionRepository optionRepository;
    @Mock
    private OrderProductRepository orderProductRepository;
    @Mock
    private OrderProductOptionRepository orderProductOptionRepository;

    @Mock
    private TokenProvider tokenProvider;

    String token = "Bearer token";
    OrderAddDto.Request ordersAddDto = Request.builder()
        .payment(Payment.KAKAO_PAY)
        .build();
    Member member = Member.builder()
        .id(1L)
        .password("password")
        .nickname("testNickname")
        .phone("01022223333")
        .email("test@naber.com")
        .role(Role.ROLE_USER)
        .build();
    Order order = Order.fromAddOrderDto(ordersAddDto, member);
    Cart cart = Cart.builder()
        .id(1L)
        .member(member)
        .product(Product.builder().build())
        .quantity(2)
        .build();

    // yesun-23.08.31
    @BeforeEach
    public void setUp() {
        // given
        given(tokenProvider.getId(token)).willReturn(member.getId());
        given(memberRepository.findById(member.getId())).willReturn(Optional.of(member));
    }

    // yesun-23.08.31
    @Test
    @DisplayName("주문 저장 성공 - 토큰, 결제 수단을 받아 주문 저장")
    void successAddOrders() {
        // given
        given(orderRepository.save(any(Order.class))).willReturn(order);
        given(cartRepository.findAllByMember(member)).willReturn(List.of(cart));
        given(orderProductRepository.save(any(OrderProduct.class))).willReturn(
            OrderProduct.builder()
                .orderId(1L)
                .build());

        // when
        orderService.addOrder(token, ordersAddDto);

        // then
        then(orderService).should(times(1)).addOrder(token, ordersAddDto);
    }

    // yesun-23.08.31
    @Test
    @DisplayName("주문 저장 실패 - 유효하지 않는 토큰으로 요청")
    void failAddOrdersMemberNotExists() {
        // given
        given(memberRepository.findById(member.getId()))
            .willReturn(Optional.empty());

        // when, then
        assertThatThrownBy(() -> orderService.addOrder(token, ordersAddDto))
            .isExactlyInstanceOf(CustomException.class)
            .hasMessage(MEMBER_NOT_EXISTS.getMessage());
        then(orderService).should(times(1)).addOrder(token, ordersAddDto);
    }

    // yesun-23.08.31
    @Test
    @DisplayName("주문 저장 실패 - 장바구니에 담은 상품이 없음")
    void failAddOrdersProductNotExists() {
        // given
        given(cartRepository.findAllByMember(member)).willReturn(List.of());

        // when, then
        assertThatThrownBy(() -> orderService.addOrder(token, ordersAddDto))
            .isExactlyInstanceOf(CustomException.class)
            .hasMessage(CART_IS_EMPTY.getMessage());
        then(orderService).should(times(1)).addOrder(token, ordersAddDto);
    }
}