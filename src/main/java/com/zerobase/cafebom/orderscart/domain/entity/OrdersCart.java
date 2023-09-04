package com.zerobase.cafebom.orderscart.domain.entity;


import com.zerobase.cafebom.cart.domain.entity.Cart;
import com.zerobase.cafebom.common.BaseTimeEntity;
import com.zerobase.cafebom.orders.domain.entity.Orders;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
    private Orders orders;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    private Cart cart;
}
