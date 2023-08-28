package com.zerobase.cafebom.orders.repository;

import com.zerobase.cafebom.member.domain.entity.Member;
import com.zerobase.cafebom.orders.domain.entity.Orders;
import com.zerobase.cafebom.orders.service.OrdersHistoryService;
import com.zerobase.cafebom.security.Role;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@SpringBootTest
@Transactional
public class OrdersRepositoryTest {


  @MockBean
  private OrdersRepository ordersRepository;



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

  @Test
  @DisplayName("특정 회원의 주문 조회 - 성공")
  public void successGetOrdersByMember() {
    // given
    Member member = Member.builder()
            .email("")
            .phone("")
            .role(Role.ROLE_USER)
            .id(2L)
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

  @Test
  @DisplayName("특정 기간 동안 회원의 주문 조회 - 성공")
  public void successGetOrdersByMemberAndDates() {
    // given
    Member member = Member.builder()
            .email("")
            .phone("")
            .role(Role.ROLE_USER)
            .id(3L)
            .nickname("")
            .build();
    LocalDateTime startDate = LocalDateTime.now().minusDays(7);
    LocalDateTime endDate = LocalDateTime.now();
    List<Orders> ordersList = new ArrayList<>();
    when(ordersRepository.findByMemberAndCreatedDateBetween(member, startDate, endDate))
            .thenReturn(ordersList);

    // when
    List<Orders> result = ordersRepository.findByMemberAndCreatedDateBetween(member, startDate, endDate);

    // then
    verify(ordersRepository, times(1)).findByMemberAndCreatedDateBetween(member, startDate, endDate);
  }
}