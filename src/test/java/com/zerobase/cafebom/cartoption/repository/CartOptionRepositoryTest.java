package com.zerobase.cafebom.cartoption.repository;

import com.zerobase.cafebom.cartoption.domain.CartOptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class CartOptionRepositoryTest {

    @Autowired
    private CartOptionRepository cartOptionRepository;
}