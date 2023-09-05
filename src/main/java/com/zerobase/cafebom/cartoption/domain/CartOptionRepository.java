package com.zerobase.cafebom.cartoption.domain;

import com.zerobase.cafebom.cart.domain.Cart;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartOptionRepository extends JpaRepository<CartOption, Long> {

    List<CartOption> findAllByCart(Cart cart);

    List<CartOption> findAllByCart(Long cartId);
}