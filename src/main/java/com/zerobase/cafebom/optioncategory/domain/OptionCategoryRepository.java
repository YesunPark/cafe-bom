package com.zerobase.cafebom.optioncategory.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OptionCategoryRepository extends JpaRepository<OptionCategory, Integer> {

}
