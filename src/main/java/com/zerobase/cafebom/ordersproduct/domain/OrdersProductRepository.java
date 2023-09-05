package com.zerobase.cafebom.ordersproduct.domain;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdersProductRepository extends JpaRepository<OrdersProduct, Long> {

    List<OrdersProduct> findByOrdersId(Long ordersId);
}