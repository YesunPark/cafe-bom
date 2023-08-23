package com.zerobase.cafebom.pay.service;

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
}
