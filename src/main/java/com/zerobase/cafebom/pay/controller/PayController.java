package com.zerobase.cafebom.pay.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PayController {

    @RequestMapping("/pay")
    public String pay(Model model) {
        model.addAttribute("totalAmount", 20000);
        return "pay";
    }
}
