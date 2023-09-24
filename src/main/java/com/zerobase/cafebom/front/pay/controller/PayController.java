package com.zerobase.cafebom.front.pay.controller;

import com.zerobase.cafebom.front.pay.service.impl.PayService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import springfox.documentation.annotations.ApiIgnore;

@Tag(name = "pay-controller", description = "결제 API")
@Controller
@RequiredArgsConstructor
@RequestMapping("/auth/pay")
public class PayController {

    private final PayService payService;

    // 카카오 QR 테스트 결제-yesun-23.08.24
    @GetMapping("/kakao")
    public @ApiIgnore String kakaoQRPay(Model model) {
        payService.payKakaoQR(model);
        return "pay";
    }
}