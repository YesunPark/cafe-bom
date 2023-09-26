package com.zerobase.cafebom.front.order.domain;

import com.zerobase.cafebom.front.member.domain.Member;
import com.zerobase.cafebom.common.type.OrderReceiptStatus;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByMemberAndCreatedDateAfter(Member member, LocalDateTime createdDate);

    List<Order> findByMember(Member member);

    List<Order> findByMemberAndCreatedDateBetween(Member member, LocalDateTime startDate,
        LocalDateTime endDate);

    List<Order> findByReceiptStatus(OrderReceiptStatus receiptStatus);
}