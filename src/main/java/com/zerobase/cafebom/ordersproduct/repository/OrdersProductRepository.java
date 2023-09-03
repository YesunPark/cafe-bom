package com.zerobase.cafebom.ordersproduct.repository;

import com.zerobase.cafebom.orders.domain.entity.Orders;
import com.zerobase.cafebom.ordersproduct.domain.entity.OrdersProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrdersProductRepository extends JpaRepository<OrdersProduct, Long> {

    List<OrdersProduct> findByOrdersId(Long ordersId);
}