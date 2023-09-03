package com.zerobase.cafebom.orders.controller;

import com.zerobase.cafebom.exception.CustomException;
import com.zerobase.cafebom.member.repository.MemberRepository;
import com.zerobase.cafebom.orders.service.OrdersHistoryService;
import com.zerobase.cafebom.orders.service.dto.OrdersHisDto;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.zerobase.cafebom.orders.controller.form.OrdersAddForm;
import com.zerobase.cafebom.orders.service.OrdersService;
import com.zerobase.cafebom.orders.service.dto.OrdersAddDto;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import java.time.LocalDate;
import java.util.List;

import static com.zerobase.cafebom.exception.ErrorCode.INVALID_INPUT;


@Tag(name = "orders-controller", description = "주문 관련 API")
@Controller
@RequiredArgsConstructor
@RequestMapping("/auth/pay")
public class OrdersController {

    private final OrdersHistoryService orderService;

    private final MemberRepository memberRepository;

    private final OrdersService ordersService;

    // yesun-23.08.31
    @ApiOperation(value = "주문 내역 저장",
        notes = "사용자의 토큰을 받아 현재 장바구니에 담겨있는 목록들을 주문 내역 테이블에 저장합니다.")
    @PostMapping
    public ResponseEntity<?> ordersAdd(@RequestHeader(name = "Authorization") String token,
        @Valid @RequestBody OrdersAddForm.Request ordersAddForm) {
        ordersService.addOrders(token, OrdersAddDto.Request.from(ordersAddForm));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    // youngseon-23.08.28
    @GetMapping("/list")
    public ResponseEntity<?> getOrderHistoryList(
        @RequestParam("memberId") Long memberId,
        @RequestParam(value = "viewType", required = false) String viewType,
        @RequestParam(value = "startDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
        @RequestParam(value = "endDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {

        if ("기간".equals(viewType) && (startDate == null || endDate == null)) {

            throw new CustomException(INVALID_INPUT);
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