package com.zerobase.cafebom.admin.order.controller;

import static org.springframework.http.HttpStatus.NO_CONTENT;

import com.zerobase.cafebom.admin.service.AdminOrderService;
import io.swagger.annotations.ApiOperation;
import com.zerobase.cafebom.front.order.dto.OrderCookingTimeModifyDto;
import com.zerobase.cafebom.front.order.dto.OrderCookingTimeModifyForm;
import com.zerobase.cafebom.front.order.dto.OrderReceiptModifyDto;
import com.zerobase.cafebom.front.order.dto.OrderReceiptModifyForm;
import com.zerobase.cafebom.front.order.dto.OrderStatusModifyDto;
import com.zerobase.cafebom.front.order.dto.OrderStatusModifyForm;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "admin-order-controller", description = "관리자 주문 관련 API")
@RestController
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/order")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class AdminOrderController {

    private final AdminOrderService adminOrderService;

    // minsu-23.09.20
    @ApiOperation(value = "주문 상태 변경", notes = "관리자가 주문 상태를 변경합니다.")
    @PatchMapping("/status/{orderId}")
    public ResponseEntity<Void> orderStatusModify(
        @PathVariable Long orderId,
        @RequestBody @Valid OrderStatusModifyForm orderStatusModifyForm) {

        adminOrderService.modifyOrderStatus(orderId,
            OrderStatusModifyDto.from(orderStatusModifyForm));
        return ResponseEntity.status(NO_CONTENT).build();
    }

    // minsu-23.09.20
    @ApiOperation(value = "주문 수락 또는 거절", notes = "관리자가 주문을 수락 또는 거절합니다.")
    @PatchMapping("/receipt-status/{orderId}")
    public ResponseEntity<Void> orderReceiptModify(
        @PathVariable Long orderId,
        @RequestBody @Valid OrderReceiptModifyForm orderReceiptModifyForm) {

        adminOrderService.modifyOrderReceiptStatus(orderId,
            OrderReceiptModifyDto.from(orderReceiptModifyForm));
        return ResponseEntity.status(NO_CONTENT).build();
    }

    // minsu-23.09.20
    @ApiOperation(value = "주문 조리 예정 시간 선택", notes = "관리자가 수락된 주문 조리 예정 시간을 선택합니다.")
    @PatchMapping("/cooking-time/{orderId}")
    public ResponseEntity<Void> orderCookingTimeModify(
        @PathVariable Long orderId,
        @RequestBody @Valid OrderCookingTimeModifyForm cookingTimeModifyForm) {

        adminOrderService.modifyOrderCookingTime(orderId,
            OrderCookingTimeModifyDto.from(cookingTimeModifyForm));
        return ResponseEntity.status(NO_CONTENT).build();
    }
}
