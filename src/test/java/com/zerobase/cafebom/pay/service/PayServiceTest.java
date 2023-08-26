package com.zerobase.cafebom.pay.service;

import static org.mockito.BDDMockito.given;

import com.zerobase.cafebom.member.repository.MemberRepository;
import com.zerobase.cafebom.security.TokenProvider;
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


    @Test
    @DisplayName("주문 저장 성공")
    void successAddOrders() {
        // given
//        given(tokenProvider.getId(token)).willReturn(2L);
//        given(memberRepository.findById(request.getUserId()))
//            .willReturn(Optional.ofNullable(userEntity));
//        given(accountRepository.save(any())).willReturn(accountEntity);

        // when

        // then
    }
}