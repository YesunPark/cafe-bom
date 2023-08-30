package com.zerobase.cafebom.option.domain.entity;

import com.zerobase.cafebom.common.BaseTimeEntity;
import com.zerobase.cafebom.option.controller.form.OptionModifyForm;
import com.zerobase.cafebom.optioncategory.domain.entity.OptionCategory;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@Builder(toBuilder = true)
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

    public static Option modifyOption(Option option, OptionModifyForm form, OptionCategory optionCategory) {
        return Option.builder()
                .optionCategory(optionCategory)
                .name(form.getName())
                .price(form.getPrice())
                .build();
    }
}
