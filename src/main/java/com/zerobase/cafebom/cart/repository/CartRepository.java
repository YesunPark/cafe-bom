package com.zerobase.cafebom.cart.repository;

import com.zerobase.cafebom.cart.domain.entity.Cart;
import com.zerobase.cafebom.member.domain.entity.Member;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    List<Cart> findAllByMember(Member member);

    void deleteAllByMember(Member member);

    List<Cart> findAllByMemberAndStatusBeforeOrder(Long memberId);

}
