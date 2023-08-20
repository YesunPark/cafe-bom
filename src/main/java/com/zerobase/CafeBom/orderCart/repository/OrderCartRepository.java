package com.zerobase.CafeBom.orderCart.repository;

import com.zerobase.CafeBom.orderCart.domain.entity.OrderCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderCartRepository extends JpaRepository<OrderCart, Integer> {
}
