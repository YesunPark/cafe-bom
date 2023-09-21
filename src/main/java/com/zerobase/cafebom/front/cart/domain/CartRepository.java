package com.zerobase.cafebom.front.cart.domain;

import com.zerobase.cafebom.front.member.domain.Member;
import com.zerobase.cafebom.front.product.domain.Product;
import com.zerobase.cafebom.common.type.CartOrderStatus;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    List<Cart> findAllByMember(Member member);

    List<Cart> findAllByMemberAndStatus(Long memberId, CartOrderStatus status);

    void deleteAllByMember(Member member);

    List<Cart> findAllByMemberAndStatus(Member member, CartOrderStatus status);

    List<Cart> findByMemberAndProduct(Member member , Product product);

    List<Cart> findByMember(Member member);
}
