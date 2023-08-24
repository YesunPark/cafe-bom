package com.zerobase.cafebom.ordersproductoption.repository;

import com.zerobase.cafebom.ordersproductoption.domain.entity.OrdersProductOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdersProductOptionRepository extends JpaRepository<OrdersProductOption, Long> {

}