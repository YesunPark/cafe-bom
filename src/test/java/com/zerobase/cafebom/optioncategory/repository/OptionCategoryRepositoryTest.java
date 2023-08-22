package com.zerobase.cafebom.optioncategory.repository;

import com.zerobase.cafebom.optioncategory.repository.OptionCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class OptionCategoryRepositoryTest {

    @Autowired
    private OptionCategoryRepository optionCategoryRepository;
}