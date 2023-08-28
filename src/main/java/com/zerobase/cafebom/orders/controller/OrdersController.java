package com.zerobase.cafebom.orders.controller;

import com.zerobase.cafebom.exception.CustomException;
import com.zerobase.cafebom.exception.ErrorCode;
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
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

import static com.zerobase.cafebom.exception.ErrorCode.MEMBER_NOT_EXISTS;

@Controller
@RequiredArgsConstructor
public class OrdersController {



    private final OrdersHistoryService orderService;


    private final MemberRepository memberRepository;


    // youngseon 23.08.28
    @GetMapping("/auth/pay-list")
    public ResponseEntity<?> getOrderHistoryList(
            @RequestParam("memberId") Long memberId,
            @RequestParam(value = "viewType", required = false) String viewType,
            @RequestParam(value = "startDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam(value = "endDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {




        if ("기간".equals(viewType) && (startDate == null || endDate == null)) {

            throw new CustomException(MEMBER_NOT_EXISTS);

            //throw new ResponseStatusException(HttpStatus.NOT_FOUND, "기간별 조회에는 시작일과 종료일이 모두 필요합니다.");
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