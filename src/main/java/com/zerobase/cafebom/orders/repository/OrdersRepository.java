package com.zerobase.cafebom.orders.repository;

import com.zerobase.cafebom.orders.domain.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdersRepository extends JpaRepository<Orders,Long> {

}