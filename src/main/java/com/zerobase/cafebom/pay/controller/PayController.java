package com.zerobase.cafebom.pay.controller;

import com.zerobase.cafebom.pay.service.PayService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/auth/pay")
public class PayController {

    private final PayService payService;

    @GetMapping
    public String payKakaoQR(Model model) {
        payService.payKakaoQR(model);
        return "pay";
    }
}
