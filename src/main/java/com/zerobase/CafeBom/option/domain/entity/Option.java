package com.zerobase.CafeBom.option.domain.entity;

import com.zerobase.CafeBom.common.BaseTimeEntity;
import com.zerobase.CafeBom.optionCategory.domain.entity.OptionCategory;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@Builder
public class Option extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OPTION_CATEGORY_ID")
    private OptionCategory optionCategory;

    @NotNull
    @Column(unique = true)
    private String name;

    @NotNull
    private Integer price;

}
