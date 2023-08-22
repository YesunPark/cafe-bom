package com.zerobase.cafebom.option.repository;

import com.zerobase.cafebom.option.domain.entity.Option;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OptionRepository extends JpaRepository<Option, Integer> {

}
