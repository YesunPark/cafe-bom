package com.zerobase.cafebom.orders.controller;

import com.zerobase.cafebom.orders.service.OrdersService;
import com.zerobase.cafebom.orders.controller.form.OrdersAddForm;
import com.zerobase.cafebom.orders.service.dto.OrdersAddDto;
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

    // yesun-23.08.29
    @ApiOperation(value = "주문 내역 저장",
        notes = "주문한 상품, 상품의 옵션, 결제 수단 등을 받아 주문 테이블에 저장합니다.")
    @PostMapping
    public ResponseEntity<?> ordersAdd(
        @RequestHeader(name = "Authorization") String token,
        @Valid @RequestBody OrdersAddForm ordersAddForm
    ) {
        ordersService.addOrders(token, OrdersAddDto.Request.from(ordersAddForm));
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
