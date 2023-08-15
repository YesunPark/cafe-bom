package com.zerobase.CafeBom.menu.domain.entity;

import com.zerobase.CafeBom.common.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
public class Menu extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "category_id")
    private Category categoryId;

    @NotNull
    @Column(unique = true)
    private String name;

    private String description;

    @NotNull
    private int price;

    @ElementCollection
    private List<Integer> optionCategories = new ArrayList<>();

    @NotNull
    private byte[] picture;



}
