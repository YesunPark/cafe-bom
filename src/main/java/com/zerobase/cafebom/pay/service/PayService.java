package com.zerobase.cafebom.pay.service;

import com.zerobase.cafebom.pay.service.dto.PayOrdersDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Slf4j
@Service
@RequiredArgsConstructor
public class PayService {

    public void payKakaoQR(Model model) {
        model.addAttribute("totalAmount", 20000);
    }

    // 관리자 주문 거절 시 결제 취소-yesun-23.08.24
    public void cancelPayKakaoQR() {

    }

    // 상품 결제 및 주문-yesun-23.08.24
    public void payOrders(String token, PayOrdersDto payOrdersDto) {

    }
}
