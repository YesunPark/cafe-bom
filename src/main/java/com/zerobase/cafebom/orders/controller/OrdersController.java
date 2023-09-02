package com.zerobase.cafebom.orders.controller;

import com.zerobase.cafebom.orders.controller.form.OrdersAddForm;
import com.zerobase.cafebom.orders.controller.form.OrdersElapsedFindForm;
import com.zerobase.cafebom.orders.controller.form.OrdersStatusModifyForm;
import com.zerobase.cafebom.orders.service.OrdersService;
import com.zerobase.cafebom.orders.service.dto.OrdersAddDto;
import com.zerobase.cafebom.orders.service.dto.OrdersStatusModifyDto;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "orders-controller", description = "주문 관련 API")
@RestController
@Controller
@RequiredArgsConstructor
public class OrdersController {

    private final OrdersService ordersService;

    // minsu-23.08.23
    @ApiOperation(value = "주문 상태 변경", notes = "관리자가 주문 상태를 변경합니다.")
    @PatchMapping("/admin/orders-status/{ordersId}")
    public ResponseEntity<String> ordersStatusModify(
        @PathVariable Long ordersId,
        @RequestBody OrdersStatusModifyForm ordersStatusModifyForm) {

        ordersService.modifyOrdersStatus(ordersId,
            OrdersStatusModifyDto.from(ordersStatusModifyForm));
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    // minsu-23.08.23
    @ApiOperation(value = "조리 경과 시간 조회", notes = "사용자가 조리 경과 시간을 조회합니다")
    @GetMapping("/auth/orders-elapsed-time/{ordersId}")
    public ResponseEntity<OrdersElapsedFindForm> elapsedTimeGet(@PathVariable Long ordersId) {
        Long elapsedTimeMinutes = ordersService.getElapsedTime(ordersId);
        OrdersElapsedFindForm response = OrdersElapsedFindForm.builder()
            .elapsedTimeMinutes(elapsedTimeMinutes)
            .build();
        return ResponseEntity.ok(response);
    }

    // yesun-23.08.31
    @ApiOperation(value = "주문 내역 저장",
        notes = "사용자의 토큰을 받아 현재 장바구니에 담겨있는 목록들을 주문 내역 테이블에 저장합니다.")
    @PostMapping("/auth/pay")
    public ResponseEntity<?> ordersAdd(@RequestHeader(name = "Authorization") String token,
        @Valid @RequestBody OrdersAddForm.Request ordersAddForm) {
        ordersService.addOrders(token, OrdersAddDto.Request.from(ordersAddForm));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
