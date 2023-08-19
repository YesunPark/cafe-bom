package com.zerobase.CafeBom.order.repository;

import com.zerobase.CafeBom.order.domain.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {

}