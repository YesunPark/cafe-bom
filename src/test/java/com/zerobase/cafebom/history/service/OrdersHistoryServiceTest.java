package com.zerobase.cafebom.history.service;

import com.zerobase.cafebom.member.domain.entity.Member;
import com.zerobase.cafebom.member.repository.MemberRepository;
import com.zerobase.cafebom.orders.domain.entity.Orders;
import com.zerobase.cafebom.orders.repository.OrdersRepository;
import com.zerobase.cafebom.orders.service.OrdersHistoryService;
import com.zerobase.cafebom.orders.service.dto.OrdersHisDto;
import com.zerobase.cafebom.ordersproduct.domain.entity.OrdersProduct;
import com.zerobase.cafebom.ordersproduct.repository.OrdersProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrdersHistoryServiceTest {

    @Mock
    private OrdersRepository orderRepository;

    @Mock
    private OrdersProductRepository orderCartRepository;

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private OrdersHistoryService orderService;

    private Member testMember;
    private List<Orders> testOrders;

    @BeforeEach
    public void setUp() {
        // 테스트에 사용할 Member 객체 생성
        testMember = Member.builder()
                .id(1L)
                .build();

        // 테스트에 사용할 Order 객체들 생성
        testOrders = new ArrayList<>();
        Orders order1 = Orders.builder()
                .id(1L)
                .member(testMember)
                .build();
        testOrders.add(order1);

        Orders order2 = Orders.builder()
                .id(2L)
                .member(testMember)
                .build();
        testOrders.add(order2);
    }

    @Test
    public void successGetOrderHistoryFor3Months() {
        // Given
        LocalDateTime threeMonthsAgo = LocalDateTime.now().minusMonths(3);

        // Mock 객체에 행동 지정
        when(memberRepository.findById(1L)).thenReturn(Optional.of(testMember));
        when(orderRepository.findByMemberAndCreatedDateAfter(eq(testMember), any(LocalDateTime.class)))
                .thenReturn(testOrders.subList(0, 1));

        // When
        List<OrdersHisDto> result = orderService.findOrderHistoryFor3Months(1L);

        // Then
        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getOrderId());
    }

    @Test
    public void failGetOrderHistoryFor3MonthsUserNotFound() {
        // Given
        when(memberRepository.findById(1L)).thenReturn(Optional.empty());

        // When, Then
        assertThrows(ResponseStatusException.class, () -> {
            orderService.findOrderHistoryFor3Months(1L);
        });
    }

    @Test
    public void successGetAllOrderHistory() {
        // Given
        when(memberRepository.findById(1L)).thenReturn(Optional.of(testMember));
        when(orderRepository.findByMember(testMember)).thenReturn(testOrders);
        // findByMember 에서 findByMemberId 로 변경  testMember 에서 testMember.getId()로 변경

        // When
        List<OrdersHisDto> result = orderService.findAllOrderHistory(1L);

        // Then
        assertEquals(2, result.size()); // 모든 주문 반환
    }

    @Test
    public void failGetAllOrderHistoryUserNotFound() {
        // Given
        when(memberRepository.findById(1L)).thenReturn(Optional.empty());

        // When, Then
        assertThrows(ResponseStatusException.class, () -> {
            orderService.findAllOrderHistory(1L);
        });
    }

    @Test
    public void successGetOrderHistoryByPeriod() {
        // Given
        LocalDateTime startDate = LocalDate.now().minusMonths(2).atStartOfDay();
        LocalDateTime endDate = LocalDate.now().atTime(23, 59, 59);

        when(memberRepository.findById(1L)).thenReturn(Optional.of(testMember));
        when(orderRepository.findByMemberAndCreatedDateBetween(testMember, startDate, endDate))
                .thenReturn(testOrders.subList(0, 1)); // 주어진 기간 내 주문만 반환

        // When
        List<OrdersHisDto> result = orderService.findOrderHistoryByPeriod(1L, LocalDate.now().minusMonths(2), LocalDate.now());

        // Then
        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getOrderId());
    }

    @Test
    public void failGetOrderHistoryByPeriodUserNotFound() {
        // Given
        when(memberRepository.findById(1L)).thenReturn(Optional.empty());

        // When, Then
        assertThrows(ResponseStatusException.class, () -> {
            orderService.findOrderHistoryByPeriod(1L, LocalDate.now().minusMonths(2), LocalDate.now());
        });
    }
}