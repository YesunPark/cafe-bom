package com.zerobase.cafebom.front.order.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdersProductOptionRepository extends JpaRepository<OrdersProductOption, Long> {

}