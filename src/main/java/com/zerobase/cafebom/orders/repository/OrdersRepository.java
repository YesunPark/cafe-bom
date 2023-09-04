package com.zerobase.cafebom.orders.repository;

import com.zerobase.cafebom.member.domain.entity.Member;
import com.zerobase.cafebom.orders.domain.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Long> {

    List<Orders> findByMemberAndCreatedDateAfter(Member member, LocalDateTime createdDate);

    List<Orders> findByMember(Member member);

    List<Orders> findByMemberAndCreatedDateBetween(Member member, LocalDateTime startDate,
        LocalDateTime endDate);
}