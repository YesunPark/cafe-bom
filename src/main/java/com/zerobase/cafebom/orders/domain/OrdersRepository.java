package com.zerobase.cafebom.orders.domain;

import com.zerobase.cafebom.member.domain.Member;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Long> {

    List<Orders> findByMemberAndCreatedDateAfter(Member member, LocalDateTime createdDate);

    List<Orders> findByMember(Member member);

    List<Orders> findByMemberAndCreatedDateBetween(Member member, LocalDateTime startDate,
        LocalDateTime endDate);
}