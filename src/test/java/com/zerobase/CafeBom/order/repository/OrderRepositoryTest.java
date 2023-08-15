package com.zerobase.CafeBom.order.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.zerobase.CafeBom.order.domain.entity.Order;
import com.zerobase.CafeBom.order.domain.type.OrderStatus;
import com.zerobase.CafeBom.user.domain.entity.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.Transactional;

@SpringJUnitConfig
@DataJpaTest
@Transactional
public class OrderRepositoryTest {

  @Autowired
  private OrderRepository orderRepository;

  private Member member;
  private Order order;

  @BeforeEach
  void setUp() {
    member = Member.builder()
        .password("1111")
        .nickname("민수")
        .phone("010-0000-0000")
        .email("test@example.com")
        .build();

    order = Order.builder()
        .member(member)
        .payment("신용 카드")
        .menus("커피")
        .status(OrderStatus.PENDING)
        .cookingTime("15 분")
        .isCanceled(false)
        .build();
  }

  // minsu-23.08.15
  @Test
  @DisplayName("주문 저장")
  void saveOrder() {
    // When
    Order savedOrder = orderRepository.save(order);

    // Then
    assertThat(savedOrder.getId()).isNotNull();
  }

  // minsu-23.08.15
  @Test
  @DisplayName("주문 조회")
  void findOrder() {
    // Given
    Order savedOrder = orderRepository.save(order);
    Long orderId = savedOrder.getId();

    // When
    Order retrievedOrder = orderRepository.findById(orderId).orElse(null);

    // Then
    assertThat(retrievedOrder).isNotNull();
    assertThat(retrievedOrder.getMenus()).isEqualTo("커피");
    assertThat(retrievedOrder.getStatus()).isEqualTo(OrderStatus.PENDING);
  }

  // minsu-23.08.15
  @Test
  @DisplayName("주문 업데이트")
  void updateOrder() {
    // Given
    Order savedOrder = orderRepository.save(order);
    Long orderId = savedOrder.getId();

    // When
    Order retrievedOrder = orderRepository.findById(orderId).orElse(null);
    retrievedOrder.setMenus("에스프레소");
    orderRepository.save(retrievedOrder);

    // Then
    Order updatedOrder = orderRepository.findById(orderId).orElse(null);
    assertThat(updatedOrder).isNotNull();
    assertThat(updatedOrder.getMenus()).isEqualTo("에스프레소");
  }

  // minsu-23.08.15
  @Test
  @DisplayName("주문 삭제")
  void deleteOrder() {
    // Given
    Order savedOrder = orderRepository.save(order);
    Long orderId = savedOrder.getId();

    // When
    orderRepository.deleteById(orderId);

    // Then
    assertThat(orderRepository.findById(orderId)).isEmpty();
  }
}