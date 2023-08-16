package com.zerobase.CafeBom.option.repository;

import com.zerobase.CafeBom.option.domain.entity.MenuOptionCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuOptionCategoryRepository extends JpaRepository<MenuOptionCategory, Integer> {
}
