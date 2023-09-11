package com.zerobase.cafebom.cartoption.domain;

import com.zerobase.cafebom.cart.domain.Cart;
import com.zerobase.cafebom.option.domain.Option;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartOptionRepository extends JpaRepository<CartOption, Long> {

    List<CartOption> findAllByCart(Cart cart);

    List<CartOption> findByCart(Cart cart);

    void deleteAllByCart(Cart cart);

    CartOption findByCartAndOption(Cart cart, Option option);

    List<Integer> findOptionIdsByCart(Cart cart); // 추가 2023-09-08 10:13



    List<CartOption> findAllByCart(Long cartId);
}