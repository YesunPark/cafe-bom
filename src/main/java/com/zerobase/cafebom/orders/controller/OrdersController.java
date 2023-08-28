package com.zerobase.cafebom.orders.controller;

import com.zerobase.cafebom.orders.controller.form.OrdersStatusModifyForm;
import com.zerobase.cafebom.orders.service.OrdersService;
import com.zerobase.cafebom.orders.service.dto.OrdersStatusModifyDto;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<String> updateOrdersStatus(
        @PathVariable Long ordersId,
        @RequestBody OrdersStatusModifyForm ordersStatusForm) {

        ordersService.updateOrdersStatus(ordersId, OrdersStatusModifyDto.from(ordersStatusForm));
        return ResponseEntity.ok().build();
    }

    // minsu-23.08.23
    @ApiOperation(value = "조리 경과 시간 조회", notes = "사용자가 조리 경과 시간을 조회합니다")
    @GetMapping("/auth/orders-elapsed-time/{ordersId}")
    public ResponseEntity<Long> getElapsedTime(@PathVariable Long ordersId) {
        Long elapsedTimeMinutes = ordersService.getElapsedTime(ordersId);
        return ResponseEntity.ok(elapsedTimeMinutes);
    }
}