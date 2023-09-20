package com.zerobase.cafebom.front.order.controller;

import static com.zerobase.cafebom.common.exception.ErrorCode.START_DATE_AND_END_DATE_ARE_ESSENTIAL;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

import com.zerobase.cafebom.common.exception.CustomException;
import com.zerobase.cafebom.front.order.dto.OrdersAddDto;
import com.zerobase.cafebom.front.order.dto.OrdersAddForm;
import com.zerobase.cafebom.front.order.dto.OrdersElapsedFindForm;
import com.zerobase.cafebom.front.order.dto.OrdersHisDto;
import com.zerobase.cafebom.front.order.service.impl.OrdersHistoryService;
import com.zerobase.cafebom.front.order.service.impl.OrdersService;
import com.zerobase.cafebom.common.config.security.TokenProvider;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.time.LocalDate;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "orders-controller", description = "사용자 주문 관련 API")
@RestController
@Controller
@RequiredArgsConstructor
@RequestMapping("/auth/orders")
public class OrdersController {

    private final OrdersHistoryService orderService;

    private final OrdersService ordersService;

    private final TokenProvider tokenProvider;

    // minsu-23.09.12
    @ApiOperation(value = "조리 경과 시간 조회", notes = "사용자가 조리 경과 시간을 조회합니다.")
    @GetMapping("/elapsed-time/{ordersId}")
    public ResponseEntity<OrdersElapsedFindForm> elapsedTimeGet(@PathVariable Long ordersId) {
        Long elapsedTimeMinutes = ordersService.findElapsedTime(ordersId);
        OrdersElapsedFindForm response = OrdersElapsedFindForm.builder()
            .elapsedTimeMinutes(elapsedTimeMinutes)
            .build();
        return ResponseEntity.ok(response);
    }

    // minsu-23.09.12
    @ApiOperation(value = "주문 취소", notes = "관리자가 주문을 수락하기 전에 사용자가 주문을 취소합니다.")
    @PatchMapping("/cancel/{ordersId}")
    public ResponseEntity<Void> ordersCancelModify(@PathVariable Long ordersId) {
        ordersService.modifyOrdersCancel(ordersId);
        return ResponseEntity.status(NO_CONTENT).build();
    }

    // yesun-23.09.05
    @ApiOperation(value = "주문 내역 저장",
        notes = "사용자의 토큰을 받아 현재 장바구니에 담겨있는 목록들을 주문 내역 테이블에 저장합니다.")
    @PostMapping
    public ResponseEntity<Void> ordersAdd(@RequestHeader(name = "Authorization") String token,
        @Valid @RequestBody OrdersAddForm.Request ordersAddForm) {
        ordersService.addOrders(token, OrdersAddDto.Request.from(ordersAddForm));
        return ResponseEntity.status(CREATED).build();
    }

    // youngseon-23.09.05
    @ApiOperation(value = "주문 내역 조회",
        notes = "기본적으로 3개월간의 내역이 조회되고, 기간 별 필터링을 해서 조회할 수 있습니다.")
    @GetMapping("/list")
    public ResponseEntity<List<OrdersHisDto>> getOrderHistoryList(
        @RequestHeader(name = "Authorization") String token,
        @RequestParam(value = "viewType", required = false) String viewType,
        @RequestParam(value = "startDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
        @RequestParam(value = "endDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {

        Long memberId = tokenProvider.getId(token);

        if ("기간".equals(viewType) && (startDate == null || endDate == null)) {
            throw new CustomException(START_DATE_AND_END_DATE_ARE_ESSENTIAL);
        }

        List<OrdersHisDto> orderHisDtoList;

        if ("전체".equals(viewType)) {
            orderHisDtoList = orderService.findAllOrderHistory(memberId);
        } else if ("기간".equals(viewType) && startDate != null && endDate != null) {
            orderHisDtoList = orderService.findOrderHistoryByPeriod(memberId, startDate, endDate);
        } else {
            orderHisDtoList = orderService.findOrderHistoryFor3Months(memberId);
        }

        return ResponseEntity.ok(orderHisDtoList);
    }
}