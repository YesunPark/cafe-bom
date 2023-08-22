<<<<<<< HEAD:src/test/java/com/zerobase/CafeBom/order/repository/OrderRepositoryTest.java
package com.zerobase.cafebom.order.repository;
=======
package com.zerobase.cafebom.orders.repository;
>>>>>>> main:src/test/java/com/zerobase/cafebom/orders/repository/OrderRepositoryTest.java

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class OrderRepositoryTest {

  @Autowired
  private OrderRepository orderRepository;

}