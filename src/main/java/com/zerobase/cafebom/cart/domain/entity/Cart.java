package com.zerobase.cafebom.cart.domain.entity;

import com.zerobase.cafebom.cart.domain.entity.type.CartOrderStatus;
import com.zerobase.cafebom.common.BaseTimeEntity;
import com.zerobase.cafebom.member.domain.entity.Member;
import com.zerobase.cafebom.product.domain.entity.Product;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
public class Cart extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @NotNull
    private Integer count;

    @NotNull
    private CartOrderStatus status;
}