package com.zerobase.CafeBom.cart.repository;

import com.zerobase.CafeBom.cart.domain.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
}
