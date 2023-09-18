package com.zerobase.cafebom.cart.domain;

import com.zerobase.cafebom.member.domain.Member;
import com.zerobase.cafebom.product.domain.Product;
import com.zerobase.cafebom.type.CartOrderStatus;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    List<Cart> findAllByMember(Member member);

    void deleteAllByMember(Member member);

    List<Cart> findAllByMemberAndStatus(Long memberId, CartOrderStatus status);

    List<Cart> findByMemberAndProduct(Member member , Product product);

    List<Cart> findByMember(Member member);

    void deleteByProduct(Product product);
}
