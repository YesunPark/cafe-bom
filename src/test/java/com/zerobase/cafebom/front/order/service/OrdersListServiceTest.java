package com.zerobase.cafebom.front.order.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.zerobase.cafebom.front.order.service.impl.OrdersHistoryService;
import com.zerobase.cafebom.front.member.domain.Member;
import com.zerobase.cafebom.front.member.domain.MemberRepository;
import com.zerobase.cafebom.front.order.domain.Orders;
import com.zerobase.cafebom.front.order.domain.OrdersRepository;
import com.zerobase.cafebom.front.order.dto.OrdersHisDto;
import com.zerobase.cafebom.front.order.domain.OrdersProductRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class OrdersListServiceTest {

    @InjectMocks
    private OrdersHistoryService ordersHistoryService;

    @Mock
    private OrdersRepository ordersRepository;

    @Mock
    private OrdersProductRepository ordersProductRepository;

    @Mock
    private MemberRepository memberRepository;

    // youngseon-23.08.28
    @Test
    @DisplayName("3개월 주문 내역 조회 - 성공")
    public void successGetOrderHistoryFor3Months() {
        // given
        Long memberId = 1L;
        Member testMember = Member.builder().build();
        LocalDateTime threeMonthsAgo = LocalDateTime.now().minusMonths(3);
        List<Orders> testOrders = new ArrayList<>();
        when(memberRepository.findById(memberId)).thenReturn(Optional.of(testMember));
        when(ordersRepository.findByMemberAndCreatedDateAfter(testMember,
            threeMonthsAgo)).thenReturn(testOrders);

        // when
        List<OrdersHisDto> result = ordersHistoryService.findOrderHistoryFor3Months(memberId);

        // then
        assertThat(result).isEmpty();
    }

    // youngseon-23.08.28
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

    // youngseon-23.08.28
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
        when(ordersRepository.findByMemberAndCreatedDateBetween(testMember, startDate,
            endDate)).thenReturn(testOrders);

        // when
        List<OrdersHisDto> result = ordersHistoryService.findOrderHistoryByPeriod(memberId,
            LocalDate.now().minusMonths(2), LocalDate.now());

        // then
        assertThat(result).isEmpty();
    }
}