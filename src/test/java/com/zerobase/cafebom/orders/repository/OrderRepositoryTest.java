package com.zerobase.cafebom.orders.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class OrderRepositoryTest {

  @Autowired
  private OrderRepository orderRepository;

}