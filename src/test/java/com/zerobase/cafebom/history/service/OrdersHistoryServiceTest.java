package com.zerobase.cafebom.history.service;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.zerobase.cafebom.member.domain.entity.Member;
import com.zerobase.cafebom.member.repository.MemberRepository;
import com.zerobase.cafebom.orders.domain.entity.Orders;
import com.zerobase.cafebom.orders.repository.OrdersRepository;
import com.zerobase.cafebom.orders.service.OrdersHistoryService;
import com.zerobase.cafebom.orders.service.dto.OrdersHisDto;
import com.zerobase.cafebom.ordersproduct.repository.OrdersProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
class OrdersHistoryServiceTest {

    @InjectMocks
    private OrdersHistoryService ordersHistoryService;

    @Mock
    private OrdersRepository ordersRepository;

    @Mock
    private OrdersProductRepository ordersProductRepository;

    @Mock
    private MemberRepository memberRepository;

    @Test
    @DisplayName("3개월 주문 내역 조회 - 성공")
    public void successGetOrderHistoryFor3Months() {
        // given
        Long memberId = 1L;
        Member testMember = Member.builder().build();
        LocalDateTime threeMonthsAgo = LocalDateTime.now().minusMonths(3);
        List<Orders> testOrders = new ArrayList<>();
        when(memberRepository.findById(memberId)).thenReturn(Optional.of(testMember));
        when(ordersRepository.findByMemberAndCreatedDateAfter(testMember, threeMonthsAgo)).thenReturn(testOrders);

        // when
        List<OrdersHisDto> result = ordersHistoryService.findOrderHistoryFor3Months(memberId);

        // then
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("전체 주문 내역 조회 - 성공")
    public void successGetAllOrderHistory() {
        // given
        Long memberId = 1L;
        Member testMember = Member.builder().build();
        List<Orders> testOrders = new ArrayList<>();
        when(memberRepository.findById(memberId)).thenReturn(Optional.of(testMember));
        when(ordersRepository.findByMember(testMember)).thenReturn(testOrders);

        // when
        List<OrdersHisDto> result = ordersHistoryService.findAllOrderHistory(memberId);

        // then
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("기간별 주문 내역 조회 - 성공")
    public void successGetOrderHistoryByPeriod() {
        // given
        Long memberId = 1L;
        Member testMember = Member.builder().build();
        LocalDateTime startDate = LocalDate.now().minusMonths(2).atStartOfDay();
        LocalDateTime endDate = LocalDate.now().atTime(23, 59, 59);
        List<Orders> testOrders = new ArrayList<>(); // 테스트용 Orders 리스트 생성
        when(memberRepository.findById(memberId)).thenReturn(Optional.of(testMember));
        when(ordersRepository.findByMemberAndCreatedDateBetween(testMember, startDate, endDate)).thenReturn(testOrders);

        // when
        List<OrdersHisDto> result = ordersHistoryService.findOrderHistoryByPeriod(memberId, LocalDate.now().minusMonths(2), LocalDate.now());

        // then
        assertThat(result).isEmpty();
    }
}