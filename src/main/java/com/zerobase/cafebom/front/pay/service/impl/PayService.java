package com.zerobase.cafebom.front.pay.service.impl;

import com.zerobase.cafebom.front.member.domain.MemberRepository;
import com.zerobase.cafebom.front.product.domain.OptionRepository;
import com.zerobase.cafebom.front.order.domain.OrderRepository;
import com.zerobase.cafebom.front.order.domain.OrderProductRepository;
import com.zerobase.cafebom.front.order.domain.OrderProductOptionRepository;
import com.zerobase.cafebom.front.product.domain.ProductRepository;
import com.zerobase.cafebom.common.config.security.TokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Slf4j
@Service
@RequiredArgsConstructor
public class PayService {

    private final MemberRepository memberRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final OrderProductRepository orderProductRepository;
    private final OrderProductOptionRepository orderProductOptionRepository;
    private final OptionRepository optionRepository;

    private final TokenProvider tokenProvider;

    // 카카오페이 QR 테스트 결제-yesun-23.08.23
    public void payKakaoQR(Model model) {
        model.addAttribute("totalAmount", 20000);
    }
    // 금액은 20000원으로 하드코딩. 프론트와 소통이 없다보니
    // 이 부분에서 원하는 금액을 넣는 것을 구현하기 힘듦.

    // 관리자 주문 거절 시 결제 취소-yesun-23.08.24
    public void cancelPayKakaoQR() {
        // 결제 취소 부분은 아직 요구사항이 정해지지 않았고 카카오 테스트 QR 이라 애매해서 보류
    }
}