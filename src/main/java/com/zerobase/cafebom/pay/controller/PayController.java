package com.zerobase.cafebom.pay.controller;

import com.zerobase.cafebom.pay.controller.form.OrdersAddForm;
import com.zerobase.cafebom.pay.service.PayService;
import com.zerobase.cafebom.pay.service.dto.OrdersAddDto;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    // yesun-23.08.25
    @ApiOperation(value = "결제 시 주문 저장",
        notes = "주문한 상품, 상품의 옵션, 결제 수단 등을 받아 주문 테이블에 저장합니다.")
    @PostMapping
    public ResponseEntity<?> ordersAdd(
        @RequestHeader(name = "Authorization") String token,
        @Valid @RequestBody OrdersAddForm ordersAddForm
    ) {
        payService.addOrders(token, OrdersAddDto.from(ordersAddForm));
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}