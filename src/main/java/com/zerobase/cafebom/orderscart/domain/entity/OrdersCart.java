package com.zerobase.cafebom.orderscart.domain.entity;


import com.zerobase.cafebom.cart.domain.entity.Cart;
import com.zerobase.cafebom.common.BaseTimeEntity;
import com.zerobase.cafebom.orders.domain.entity.Orders;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrdersCart extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    private Orders oder;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    private Cart cart;


}
