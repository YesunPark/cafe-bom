package com.zerobase.cafebom.pay.controller;

import com.zerobase.cafebom.pay.service.PayService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import springfox.documentation.annotations.ApiIgnore;

@Tag(name = "pay-controller", description = "카카오QR 결제 테스트 API")
@Controller
@RequiredArgsConstructor
@RequestMapping("/auth/pay")
public class PayController {

    private final PayService payService;

    // 카카오QR 테스트 결제-yesun-23.08.24
    @PostMapping
    public @ApiIgnore String payKakaoQR(Model model) {
        payService.payKakaoQR(model);
        return "pay";
    }
}