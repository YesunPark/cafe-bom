package com.zerobase.cafebom.option.repository;

import com.zerobase.cafebom.option.domain.entity.Option;
import com.zerobase.cafebom.optioncategory.domain.entity.OptionCategory;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OptionRepository extends JpaRepository<Option, Integer> {

    List<Option> findAllByOptionCategoryId(OptionCategory optionCategory);
}
