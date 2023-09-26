package com.zerobase.cafebom.front.review.domain;

import com.zerobase.cafebom.front.order.domain.OrderProduct;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    Optional<Review> findByOrderProduct(OrderProduct orderProduct);
}