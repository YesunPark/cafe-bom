package com.zerobase.CafeBom.order.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class OrdersRepositoryTest {

  @Autowired
  private OrdersRepository ordersRepository;

}