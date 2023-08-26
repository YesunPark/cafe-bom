package com.zerobase.cafebom.orders.controller;

import com.zerobase.cafebom.orders.controller.form.OrdersStatusForm;
import com.zerobase.cafebom.orders.service.OrdersService;
import com.zerobase.cafebom.orders.service.dto.OrdersStatusDto;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    @PatchMapping("/admin/orders/{ordersId}/status")
    public ResponseEntity<String> updateOrdersStatus(
        @PathVariable Long ordersId,
        @RequestBody OrdersStatusForm ordersStatusForm) {

        ordersService.updateOrdersStatus(ordersId, OrdersStatusDto.from(ordersStatusForm));
        return ResponseEntity.ok().build();
    }
}