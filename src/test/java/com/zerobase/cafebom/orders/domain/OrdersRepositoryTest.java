package com.zerobase.cafebom.orders.domain;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.zerobase.cafebom.member.domain.Member;
import com.zerobase.cafebom.orders.domain.Orders;
import com.zerobase.cafebom.orders.domain.OrdersRepository;
import com.zerobase.cafebom.security.Role;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class OrdersRepositoryTest {


    @MockBean
    private OrdersRepository ordersRepository;


    // youngseon-23.08.28
    @Test
    @DisplayName("특정 회원 및 생성일에 대한 주문 조회 - 성공")
    public void successGetOrdersByMemberAndCreatedDate() {
        // given
        Member member = Member.builder()
            .email("")
            .phone("")
            .role(Role.ROLE_USER)
            .id(1L)
            .nickname("")
            .build();
        LocalDateTime createdDate = LocalDateTime.now();
        List<Orders> ordersList = new ArrayList<>();
        when(ordersRepository.findByMemberAndCreatedDateAfter(member, createdDate))
            .thenReturn(ordersList);

        // when
        List<Orders> result = ordersRepository.findByMemberAndCreatedDateAfter(member, createdDate);

        // then
        verify(ordersRepository, times(1)).findByMemberAndCreatedDateAfter(member, createdDate);
    }

    // youngseon-23.08.28
    @Test
    @DisplayName("특정 회원의 주문 조회 - 성공")
    public void successGetOrdersByMember() {
        // given
        Member member = Member.builder()
            .email("")
            .phone("")
            .role(Role.ROLE_USER)
            .id(1L)
            .nickname("")
            .build();
        List<Orders> ordersList = new ArrayList<>();
        when(ordersRepository.findByMember(member))
            .thenReturn(ordersList);

        // when
        List<Orders> result = ordersRepository.findByMember(member);

        // then
        verify(ordersRepository, times(1)).findByMember(member);
    }

    // youngseon-23.08.28
    @Test
    @DisplayName("특정 기간 동안 회원의 주문 조회 - 성공")
    public void successGetOrdersByMemberAndDates() {
        // given
        Member member = Member.builder()
            .email("")
            .phone("")
            .role(Role.ROLE_USER)
            .id(1L)
            .nickname("")
            .build();
        LocalDateTime startDate = LocalDateTime.now().minusDays(7);
        LocalDateTime endDate = LocalDateTime.now();
        List<Orders> ordersList = new ArrayList<>();
        when(ordersRepository.findByMemberAndCreatedDateBetween(member, startDate, endDate))
            .thenReturn(ordersList);

        // when
        List<Orders> result = ordersRepository.findByMemberAndCreatedDateBetween(member, startDate,
            endDate);

        // then
        verify(ordersRepository, times(1)).findByMemberAndCreatedDateBetween(member, startDate,
            endDate);
    }
}