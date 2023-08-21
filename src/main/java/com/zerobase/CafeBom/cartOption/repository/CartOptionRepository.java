package com.zerobase.cafebom.cartOption.repository;

import com.zerobase.cafebom.cartOption.domain.entity.CartOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartOptionRepository extends JpaRepository<CartOption, Long> {

}
