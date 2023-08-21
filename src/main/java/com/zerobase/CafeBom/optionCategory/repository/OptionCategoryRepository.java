package com.zerobase.cafebom.optionCategory.repository;

import com.zerobase.cafebom.optionCategory.domain.entity.OptionCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OptionCategoryRepository extends JpaRepository<OptionCategory, Integer> {

}
