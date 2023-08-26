package com.zerobase.cafebom.orders.service;


import com.zerobase.cafebom.member.domain.entity.Member;
import com.zerobase.cafebom.member.repository.MemberRepository;
import com.zerobase.cafebom.orders.domain.entity.Orders;
import com.zerobase.cafebom.orders.repository.OrdersRepository;
import com.zerobase.cafebom.orders.service.dto.OrdersHisDto;
import com.zerobase.cafebom.orders.service.dto.OrdersProductDto;
import com.zerobase.cafebom.ordersproduct.domain.entity.OrdersProduct;
import com.zerobase.cafebom.ordersproduct.repository.OrdersProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional// 추가한 어노테이션
public class OrdersHistoryService {

    private final OrdersRepository ordersRepository;

    private final OrdersProductRepository ordersProductRepository;

    private final MemberRepository memberRepository;

    public List<OrdersHisDto> findOrderHistoryFor3Months(Long memberId) {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "사용자를 찾을수 없습니다"));

        LocalDateTime threeMonthsAgo = LocalDateTime.now().minusMonths(3);
        List<Orders> orders = ordersRepository.findByMemberAndCreatedDateAfter(member, threeMonthsAgo);
        return from(orders);
    }

    public List<OrdersHisDto> findAllOrderHistory(Long memberId) {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "사용자를 찾을수 없습니다"));

        List<Orders> orders = ordersRepository.findByMember(member); // findByMember 에서 MemberId로 변경
        return from(orders);
    }

    public List<OrdersHisDto> findOrderHistoryByPeriod(Long memberId, LocalDate startDate, LocalDate endDate) {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "사용자를 찾을수 없습니다"));

        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.atTime(23, 59, 59);
        List<Orders> orders = ordersRepository.findByMemberAndCreatedDateBetween(member, startDateTime, endDateTime);
        return from(orders);
    }

    public List<OrdersHisDto> from(List<Orders> orders) {

        List<OrdersHisDto> ordersHisDtoList = new ArrayList<>();

        for (Orders order : orders) {

            OrdersHisDto ordersHisDto = new OrdersHisDto(order);

            List<OrdersProduct> ordersProductList = ordersProductRepository.findByOrdersId(order.getId());

            for (OrdersProduct ordersProduct : ordersProductList) {

                OrdersProductDto ordersProductDto = new OrdersProductDto(ordersProduct);

                ordersHisDto.addOrderProductDto(ordersProductDto);
            }

            ordersHisDtoList.add(ordersHisDto);
        }
        return ordersHisDtoList;

    }
}
