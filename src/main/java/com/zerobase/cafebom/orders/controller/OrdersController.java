package com.zerobase.cafebom.orders.controller;

import com.zerobase.cafebom.orders.controller.form.OrdersAddForm;
import com.zerobase.cafebom.orders.service.OrdersService;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "orders-controller", description = "주문 관련 API")
@Controller
@RequiredArgsConstructor
@RequestMapping("/auth/pay")
public class OrdersController {

    private final OrdersService ordersService;

    // yesun-23.08.31
    @ApiOperation(value = "주문 내역 저장",
        notes = "사용자의 토큰을 받아 현재 장바구니에 담겨있는 목록들을 주문 내역 테이블에 저장합니다.")
    @PostMapping
    public ResponseEntity<?> ordersAdd(@RequestHeader(name = "Authorization") String token,
        @Valid @RequestBody OrdersAddForm ordersAddForm) {
        ordersService.addOrders(token, ordersAddForm);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
