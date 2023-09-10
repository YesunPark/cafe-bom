package com.zerobase.cafebom.cart.repository;

import com.zerobase.cafebom.cart.domain.entity.Cart;
import com.zerobase.cafebom.member.domain.entity.Member;
import com.zerobase.cafebom.product.domain.entity.Product;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    List<Cart> findAllByMember(Member member);

    void deleteAllByMember(Member member);

    List<Cart> findByMemberAndProduct(Member member , Product product);

    List<Cart> findByMember(Member member);

    void deleteByProduct(Product product);

}
