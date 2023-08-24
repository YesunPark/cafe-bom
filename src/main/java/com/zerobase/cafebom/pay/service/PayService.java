package com.zerobase.cafebom.pay.service;

import com.zerobase.cafebom.exception.CustomException;
import com.zerobase.cafebom.exception.ErrorCode;
import com.zerobase.cafebom.member.domain.entity.Member;
import com.zerobase.cafebom.member.repository.MemberRepository;
import com.zerobase.cafebom.member.security.TokenProvider;
import com.zerobase.cafebom.orders.domain.entity.Orders;
import com.zerobase.cafebom.orders.repository.OrdersRepository;
import com.zerobase.cafebom.pay.service.dto.AddOrdersDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Slf4j
@Service
@RequiredArgsConstructor
public class PayService {

    private final MemberRepository memberRepository;
    private final OrdersRepository ordersRepository;


    private final TokenProvider tokenProvider;

    // 카카오페이 QR 테스트 결제-yesun-23.08.23
    public void payKakaoQR(Model model) {
        model.addAttribute("totalAmount", 20000);
    }

    // 관리자 주문 거절 시 결제 취소-yesun-23.08.24
    public void cancelPayKakaoQR() {

    }

    // 상품 결제 및 주문-yesun-23.08.25
    public void addOrders(String token, AddOrdersDto addOrdersDto) {
        Long userId = tokenProvider.getId(token);
        Member member = memberRepository.findById(userId)
            .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_EXISTS));
        ordersRepository.save(Orders.fromAddOrdersDto(addOrdersDto, member));
        // 레포지토리

    }
}
