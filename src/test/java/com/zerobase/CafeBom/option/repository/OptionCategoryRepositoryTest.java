package com.zerobase.CafeBom.option.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class OptionCategoryRepositoryTest {

    @Autowired
    private OptionCategoryRepository optionCategoryRepository;

}