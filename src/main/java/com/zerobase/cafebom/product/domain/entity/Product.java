package com.zerobase.cafebom.product.domain.entity;

import com.zerobase.cafebom.common.BaseTimeEntity;
import com.zerobase.cafebom.productcategory.domain.entity.ProductCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Builder
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
public class Product extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "product_category_id")
    private ProductCategory productCategory;

    @NotNull
    @Column(unique = true)
    private String name;

    @NotNull
    private String description;

    @NotNull
    private Integer price;

    @NotNull
    private Boolean isSoldOut;

    @NotNull
    private Byte[] picture;

}