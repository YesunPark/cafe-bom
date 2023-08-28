package com.zerobase.cafebom.orders.repository;

import com.zerobase.cafebom.member.domain.entity.Member;
import com.zerobase.cafebom.orders.domain.entity.Orders;
import com.zerobase.cafebom.orders.service.OrdersHistoryService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@SpringBootTest
@Transactional
public class OrdersRepositoryTest {


  @Autowired
  private OrdersRepository ordersRepository;

  @Test
  public void givenMemberAndCreatedDate_whenFindByMemberAndCreatedDateAfter_thenVerifyRepositoryCall() {
    // given
    Member member = new Member("","",null);
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
  public void givenMember_whenFindByMember_thenVerifyRepositoryCall() {
    // given
    Member member = new Member("","",null);
    List<Orders> ordersList = new ArrayList<>();
    when(ordersRepository.findByMember(member))
            .thenReturn(ordersList);

    // when
    List<Orders> result = ordersRepository.findByMember(member);

    // then
    verify(ordersRepository, times(1)).findByMember(member);
  }

  @Test
  public void givenMemberAndDates_whenFindByMemberAndCreatedDateBetween_thenVerifyRepositoryCall() {
    // given
    Member member = new Member("","",null);
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