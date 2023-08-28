package com.zerobase.cafebom.orders.service;

import static com.zerobase.cafebom.exception.ErrorCode.MEMBER_NOT_EXISTS;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

import com.zerobase.cafebom.exception.CustomException;
import com.zerobase.cafebom.member.domain.entity.Member;
import com.zerobase.cafebom.member.repository.MemberRepository;
import com.zerobase.cafebom.option.domain.entity.Option;
import com.zerobase.cafebom.option.repository.OptionRepository;
import com.zerobase.cafebom.orders.domain.entity.Orders;
import com.zerobase.cafebom.orders.domain.type.Payment;
import com.zerobase.cafebom.orders.repository.OrdersRepository;
import com.zerobase.cafebom.orders.service.dto.OrdersAddDto;
import com.zerobase.cafebom.orders.service.dto.OrdersAddDto.OrderedProductDto;
import com.zerobase.cafebom.ordersproduct.domain.entity.OrdersProduct;
import com.zerobase.cafebom.ordersproduct.repository.OrdersProductRepository;
import com.zerobase.cafebom.ordersproductoption.repository.OrdersProductOptionRepository;
import com.zerobase.cafebom.product.domain.entity.Product;
import com.zerobase.cafebom.product.repository.ProductRepository;
import com.zerobase.cafebom.security.Role;
import com.zerobase.cafebom.security.TokenProvider;
import java.util.Arrays;
import java.util.Collections;
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
class OrdersServiceTest {

    @Spy
    @InjectMocks
    private OrdersService ordersService;

    @Mock
    private MemberRepository memberRepository;
    @Mock
    private OrdersRepository ordersRepository;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private OptionRepository optionRepository;
    @Mock
    private OrdersProductRepository ordersProductRepository;
    @Mock
    private OrdersProductOptionRepository ordersProductOptionRepository;

    @Mock
    private TokenProvider tokenProvider;

    String token = "Bearer token";

    OrdersAddDto.Request ordersAddDto = OrdersAddDto.Request.builder()
        .payment(Payment.KAKAO_PAY)
        .products(Collections.singletonList(OrderedProductDto.builder()
            .productId(1)
            .optionIds(Arrays.asList(1, 2))
            .build()))
        .build();

    Member member = Member.builder()
        .id(1L)
        .password("password")
        .nickname("testNickname")
        .phone("01022223333")
        .email("test@naber.com")
        .role(Role.ROLE_USER)
        .build();

    Orders orders = Orders.fromAddOrdersDto(ordersAddDto, member);

    Product product = Product.builder()
        .id(1)
        .name("test")
        .build();


    @BeforeEach
    public void setUp() {
        given(tokenProvider.getId(token)).willReturn(1L);
    }

    // yesun-23.08.28
    @Test
    @DisplayName("주문 저장 성공 - 결제수단, 상품/옵션 목록을 통해 주문")
    void successAddOrders() {
        // given
        given(tokenProvider.getId(token)).willReturn(member.getId());
        given(memberRepository.findById(member.getId())).willReturn(Optional.of(member));
        given(productRepository.findById(1)).willReturn(Optional.of(product));
        given(ordersRepository.save(any(Orders.class))).willReturn(orders);
        given(ordersProductRepository.save(any(OrdersProduct.class))).willReturn(
            OrdersProduct.builder()
                .ordersId(1L)
                .build());
        given(optionRepository.findById(any())).willReturn(
            Optional.ofNullable(Option.builder().id(1).build()));

        // when
        ordersService.addOrders(token, ordersAddDto);

        // then
        then(ordersService).should(times(1)).addOrders(token, ordersAddDto);
    }

    // yesun-23.08.28
    @Test
    @DisplayName("주문 저장 실패 - 존재하지 않는 회원 ID로 요청")
    void failAddOrdersMemberNotExists() {
        // given
        given(memberRepository.findById(member.getId()))
            .willReturn(Optional.empty());

        // then
        assertThatThrownBy(() -> ordersService.addOrders(token, ordersAddDto))
            .isExactlyInstanceOf(CustomException.class)
            .hasMessage(MEMBER_NOT_EXISTS.getMessage());
        then(ordersService).should(times(1)).addOrders(token, ordersAddDto);
    }
}