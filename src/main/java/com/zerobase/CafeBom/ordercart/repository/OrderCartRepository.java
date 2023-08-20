package com.zerobase.CafeBom.ordercart.repository;

import com.zerobase.CafeBom.ordercart.domain.entity.OrderCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderCartRepository extends JpaRepository<OrderCart, Integer> {
}
