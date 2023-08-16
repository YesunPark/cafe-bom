package com.zerobase.CafeBom.menu.repository;

import com.zerobase.CafeBom.menu.domain.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

}
