package com.zerobase.CafeBom.menu.domain.entity;

import com.zerobase.CafeBom.common.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@Builder
public class Menu extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CATEGORY_ID")
    private Category category;

    @NotNull
    private String name;

    @NotNull
    private String description;

    @NotNull
    private Integer price;

    @NotNull
    private String picture;

    @NotNull
    private boolean isSoldOut;

}
