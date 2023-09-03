package com.zerobase.cafebom.cart.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.cassandra.DataCassandraTest;

@DataCassandraTest
class CartRepositoryTest {

    @Autowired
    private CartRepository cartRepository;

}