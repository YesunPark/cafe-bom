package com.zerobase.cafebom.cartoption.repository;

import com.zerobase.cafebom.cart.domain.entity.Cart;
import com.zerobase.cafebom.cartoption.domain.entity.CartOption;
import com.zerobase.cafebom.option.domain.entity.Option;
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

}
