package com.zerobase.CafeBom.cartOption.repository;

import com.zerobase.CafeBom.cartOption.domain.entity.CartOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartOptionRepository extends JpaRepository<CartOption, Long> {

}
