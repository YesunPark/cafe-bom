package com.zerobase.cafebom.cartoption.repository;

import com.zerobase.cafebom.cartoption.domain.entity.CartOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartOptionRepository extends JpaRepository<CartOption, Long> {

}
