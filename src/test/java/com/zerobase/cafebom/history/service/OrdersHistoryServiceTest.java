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
import static org.assertj.core.api.Assertions.assertThat;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
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

        testMember = Member.builder()
                .id(1L)
                .build();


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
        // given
        LocalDateTime threeMonthsAgo = LocalDateTime.now().minusMonths(3);


        when(memberRepository.findById(1L)).thenReturn(Optional.of(testMember));
        when(orderRepository.findByMemberAndCreatedDateAfter(eq(testMember), any(LocalDateTime.class)))
                .thenReturn(testOrders.subList(0, 1));

        // when
        List<OrdersHisDto> result = orderService.findOrderHistoryFor3Months(1L);

        // then
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0).getOrderId()).isEqualTo(1L);
    }

    @Test
    public void failGetOrderHistoryFor3MonthsUserNotFound() {
        // given
        when(memberRepository.findById(1L)).thenReturn(Optional.empty());

        // when, then
        assertThrows(ResponseStatusException.class, () -> {
            orderService.findOrderHistoryFor3Months(1L);
        });
    }

    @Test
    public void successGetAllOrderHistory() {
        // given
        when(memberRepository.findById(1L)).thenReturn(Optional.of(testMember));
        when(orderRepository.findByMember(testMember)).thenReturn(testOrders);

        // when
        List<OrdersHisDto> result = orderService.findAllOrderHistory(1L);

        // then
        assertThat(result).hasSize(2); // 모든 주문 반환
    }

    @Test
    public void failGetAllOrderHistoryUserNotFound() {
        // given
        when(memberRepository.findById(1L)).thenReturn(Optional.empty());

        // when, then
        assertThrows(ResponseStatusException.class, () -> {
            orderService.findAllOrderHistory(1L);
        });
    }

    @Test
    public void successGetOrderHistoryByPeriod() {
        // given
        LocalDateTime startDate = LocalDate.now().minusMonths(2).atStartOfDay();
        LocalDateTime endDate = LocalDate.now().atTime(23, 59, 59);

        when(memberRepository.findById(1L)).thenReturn(Optional.of(testMember));
        when(orderRepository.findByMemberAndCreatedDateBetween(testMember, startDate, endDate))
                .thenReturn(testOrders.subList(0, 1)); // 주어진 기간 내 주문만 반환

        // when
        List<OrdersHisDto> result = orderService.findOrderHistoryByPeriod(1L, LocalDate.now().minusMonths(2), LocalDate.now());

        // then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getOrderId()).isEqualTo(1L);
    }

    @Test
    public void failGetOrderHistoryByPeriodUserNotFound() {
        // given
        when(memberRepository.findById(1L)).thenReturn(Optional.empty());

        // when, then
        assertThatThrownBy(() -> {
            orderService.findOrderHistoryByPeriod(1L, LocalDate.now().minusMonths(2), LocalDate.now());
        })
                .isInstanceOf(ResponseStatusException.class);

    }
}