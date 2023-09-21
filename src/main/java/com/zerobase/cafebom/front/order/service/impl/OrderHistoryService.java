package com.zerobase.cafebom.front.order.service.impl;


import static com.zerobase.cafebom.common.exception.ErrorCode.MEMBER_NOT_EXISTS;

import com.zerobase.cafebom.common.exception.CustomException;
import com.zerobase.cafebom.front.member.domain.Member;
import com.zerobase.cafebom.front.member.domain.MemberRepository;
import com.zerobase.cafebom.front.order.domain.Order;
import com.zerobase.cafebom.front.order.domain.OrderRepository;
import com.zerobase.cafebom.front.order.dto.OrderHisDto;
import com.zerobase.cafebom.front.order.dto.OrderProductDto;
import com.zerobase.cafebom.front.order.domain.OrderProduct;
import com.zerobase.cafebom.front.order.domain.OrderProductRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderHistoryService {

    private final OrderRepository orderRepository;

    private final OrderProductRepository orderProductRepository;

    private final MemberRepository memberRepository;

    // 지난 3개월 주문내역 조회-youngseon-23.08.28
    public List<OrderHisDto> findOrderHistoryFor3Months(Long memberId) {
        Member member = memberRepository.findById(memberId)
            .orElseThrow(() -> new CustomException(MEMBER_NOT_EXISTS));

        LocalDateTime threeMonthsAgo = LocalDateTime.now().minusMonths(3);
        List<Order> orders = orderRepository.findByMemberAndCreatedDateAfter(member,
            threeMonthsAgo);
        return from(orders);
    }

    // 모든 주문내역 조회-youngseon-23.08.28
    public List<OrderHisDto> findAllOrderHistory(Long memberId) {
        Member member = memberRepository.findById(memberId)
            .orElseThrow(() -> new CustomException(MEMBER_NOT_EXISTS));

        List<Order> orders = orderRepository.findByMember(member);
        return from(orders);
    }

    // 정해진 기간의 주문내역 조회-youngseon-23.08.28
    public List<OrderHisDto> findOrderHistoryByPeriod(Long memberId, LocalDate startDate,
        LocalDate endDate) {
        Member member = memberRepository.findById(memberId)
            .orElseThrow(() -> new CustomException(MEMBER_NOT_EXISTS));

        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.atTime(23, 59, 59);
        List<Order> orders = orderRepository.findByMemberAndCreatedDateBetween(member,
            startDateTime, endDateTime);
        return from(orders);
    }

    // 주문 정보중 사용자에게 보여줄 정보 반환-youngseon-23.08.28
    private List<OrderHisDto> from(List<Order> orders) {
        List<OrderHisDto> orderHisDtoList = new ArrayList<>();

        for (Order order : orders) {
            OrderHisDto orderHisDto = new OrderHisDto(order);

            List<OrderProduct> orderProductList = orderProductRepository.findByOrderId(
                order.getId());

            for (OrderProduct orderProduct : orderProductList) {
                OrderProductDto orderProductDto = new OrderProductDto(orderProduct);

                orderHisDto.addOrderProductDto(orderProductDto);
            }

            orderHisDtoList.add(orderHisDto);
        }
        return orderHisDtoList;
    }
}
