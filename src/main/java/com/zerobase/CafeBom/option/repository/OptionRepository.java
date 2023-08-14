package com.zerobase.CafeBom.option.repository;

import com.zerobase.CafeBom.option.domain.entity.Option;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OptionRepository extends JpaRepository<Option, Long> {

}
