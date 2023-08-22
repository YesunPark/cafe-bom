package com.zerobase.cafebom.orders.repository;

import com.zerobase.cafebom.orders.domain.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {

}