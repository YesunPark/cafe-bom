package com.zerobase.cafebom.front.cart.domain;

import com.zerobase.cafebom.front.product.domain.Option;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartOptionRepository extends JpaRepository<CartOption, Long> {

    List<CartOption> findAllByCart(Cart cart);

    List<CartOption> findByCart(Cart cart);

    void deleteAllByCart(Cart cart);

    CartOption findByCartAndOption(Cart cart, Option option);

    List<Integer> findOptionIdsByCart(Cart cart);

    List<CartOption> findAllByCart(Long cartId);
}