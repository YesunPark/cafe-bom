package com.zerobase.cafebom.ordersproduct.repository;

import com.zerobase.cafebom.ordersproduct.domain.entity.OrdersProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdersProductRepository extends JpaRepository<OrdersProduct, Long> {

}