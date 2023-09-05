package com.zerobase.cafebom.orderscart.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdersCartRepository extends JpaRepository<OrdersCart, Long> {

}
