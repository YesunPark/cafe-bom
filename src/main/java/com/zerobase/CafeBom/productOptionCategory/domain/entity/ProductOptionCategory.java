package com.zerobase.CafeBom.productOptionCategory.domain.entity;

import com.zerobase.CafeBom.common.BaseTimeEntity;
import com.zerobase.CafeBom.menu.domain.entity.Menu;
import com.zerobase.CafeBom.optionCategory.domain.entity.OptionCategory;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@Builder
public class ProductOptionCategory extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OPTION_CATEGORY_ID")
    private OptionCategory optionCategory;

}
