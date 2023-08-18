package com.zerobase.CafeBom.optionCategory.repository;

import com.zerobase.CafeBom.optionCategory.domain.entity.OptionCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OptionCategoryRepository extends JpaRepository<OptionCategory, Integer> {

}
