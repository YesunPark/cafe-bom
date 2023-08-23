package com.zerobase.cafebom.pay.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PayController {

    @RequestMapping("/pay")
    public String pay(Model model) {
        model.addAttribute("payName", "kakaoQR!!!!!!!");
        model.addAttribute("amount", 1000);
        return "pay";
    }
}
