package com.zerobase.CafeBom.ordercart.domain.entity;

import com.zerobase.CafeBom.cart.Cart;
import com.zerobase.CafeBom.common.BaseTimeEntity;
import com.zerobase.CafeBom.order.domain.Order;
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
public class OrderCart extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    private Order oder;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    private Cart cart;


}
