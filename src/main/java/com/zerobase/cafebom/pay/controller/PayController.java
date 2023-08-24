package com.zerobase.cafebom.pay.controller;

import com.zerobase.cafebom.pay.controller.form.AddOrdersForm;
import com.zerobase.cafebom.pay.service.PayService;
import com.zerobase.cafebom.pay.service.dto.AddOrdersDto;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import springfox.documentation.annotations.ApiIgnore;

@Tag(name = "pay-controller", description = "결제 API")
@Controller
@RequiredArgsConstructor
@RequestMapping("/auth/pay")
public class PayController {

    private final PayService payService;

    // 카카오 QR 테스트 결제-yesun-23.08.24
    @PostMapping("/kakao")
    public @ApiIgnore String payKakaoQR(Model model) {
        payService.payKakaoQR(model);
        return "pay";
    }

    // yesun-23.08.24
    @ApiOperation(value = "결제 시 주문 저장",
        notes = "주문한 상품, 상품의 옵션, 결제 수단 등을 받아 주문 테이블에 저장합니다.")
    @PostMapping
    public void ordersAdd(
        @RequestHeader(name = "Authorization") String token,
        @RequestBody AddOrdersForm addOrdersForm) {
        payService.addOrders(token, AddOrdersDto.from(addOrdersForm));
    }
}