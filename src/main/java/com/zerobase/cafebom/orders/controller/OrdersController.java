package com.zerobase.cafebom.orders.controller;

import com.zerobase.cafebom.orders.controller.form.OrdersCookingTimeModifyForm;
import com.zerobase.cafebom.orders.controller.form.OrdersReceiptModifyForm;
import com.zerobase.cafebom.orders.controller.form.OrdersStatusModifyForm;
import com.zerobase.cafebom.orders.service.OrdersService;
import com.zerobase.cafebom.orders.service.dto.OrdersCookingTimeModifyDto;
import com.zerobase.cafebom.orders.service.dto.OrdersElapsedFindDto;
import com.zerobase.cafebom.orders.service.dto.OrdersReceiptModifyDto;
import com.zerobase.cafebom.orders.service.dto.OrdersStatusModifyDto;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "orders-controller", description = "주문 관련 API")
@RestController
public class OrdersController {

    private final OrdersService ordersService;

    @Autowired
    public OrdersController(OrdersService ordersService) {
        this.ordersService = ordersService;
    }

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
    public ResponseEntity<OrdersElapsedFindDto> elapsedTimeGet(@PathVariable Long ordersId) {
        Long elapsedTimeMinutes = ordersService.getElapsedTime(ordersId);
        OrdersElapsedFindDto response = OrdersElapsedFindDto.builder()
            .elapsedTimeMinutes(elapsedTimeMinutes)
            .build();
        return ResponseEntity.ok(response);
    }

    // minsu-23.08.25
    @ApiOperation(value = "주문 수락 또는 거절", notes = "관리자가 주문을 수락 또는 거절합니다.")
    @PatchMapping("/admin/orders-receipt-status/{ordersId}")
    public ResponseEntity<String> ordersReceiptModify(
        @PathVariable Long ordersId,
        @RequestBody OrdersReceiptModifyForm ordersReceiptModifyForm) {

        ordersService.modifyOrdersReceiptStatus(ordersId,
            OrdersReceiptModifyDto.from(ordersReceiptModifyForm));
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    // minsu-23.08.25
    @ApiOperation(value = "주문 취소", notes = "사용자가 주문을 취소합니다.")
    @PatchMapping("/auth/orders-cancel/{ordersId}")
    public ResponseEntity<String> ordersCancelModify(@PathVariable Long ordersId) {
        ordersService.modifyOrdersCancel(ordersId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    // minsu-23.08.27
    @ApiOperation(value = "주문 조리 예정 시간 선택", notes = "관리자가 수락된 주문 조리 예정 시간을 선택합니다.")
    @PatchMapping("/admin/orders-cooking-time/{ordersId}")
    public ResponseEntity<String> ordersCookingTimeModify(
        @PathVariable Long ordersId,
        @RequestBody OrdersCookingTimeModifyForm cookingTimeModifyForm) {

        ordersService.modifyOrdersCookingTime(ordersId,
            OrdersCookingTimeModifyDto.from(cookingTimeModifyForm));
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}