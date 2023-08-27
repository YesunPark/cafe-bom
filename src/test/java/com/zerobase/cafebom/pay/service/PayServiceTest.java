package com.zerobase.cafebom.pay.service;

import static org.mockito.BDDMockito.given;

import com.zerobase.cafebom.member.domain.entity.Member;
import com.zerobase.cafebom.member.repository.MemberRepository;
import com.zerobase.cafebom.orders.domain.type.Payment;
import com.zerobase.cafebom.pay.service.dto.OrdersAddDto;
import com.zerobase.cafebom.pay.service.dto.OrdersAddDto.OrderedProductDto;
import com.zerobase.cafebom.security.TokenProvider;
import java.util.*;
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
class PayServiceTest {

    @Spy
    @InjectMocks
    private PayService payService;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private TokenProvider tokenProvider;

    String token = "Bearer token";

    OrdersAddDto.Request request;

    @BeforeEach
    public void setUp() {
        request = OrdersAddDto.Request.builder()
            .payment(Payment.KAKAO_PAY)
            .products(Collections.singletonList(OrderedProductDto.builder()
                .productId(1)
                .optionIds(Arrays.asList(1, 2))
                .build()))
            .build();
        given(tokenProvider.getId(token)).willReturn(2L);
    }


    @Test
    @DisplayName("주문 저장 성공")
    void successAddOrders() {
        // given
        given(memberRepository.findById(2L))
            .willReturn(Optional.ofNullable(Member.builder()
                .build()));
//        given(accountRepository.save(any())).willReturn(accountEntity);

        // when
        payService.addOrders(token, request);

        // then
    }
}