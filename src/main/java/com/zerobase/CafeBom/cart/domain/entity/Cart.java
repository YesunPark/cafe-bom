package com.zerobase.CafeBom.cart.domain.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "cart")
public class Cart extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToOne
    @JoinColumn(name = "menu_id")
    private Menu menu;

    @OneToMany(fetch = FetchType.EAGER)
    private List<Integer> options = new ArrayList<>();

    @NotNull
    private Integer count;
}
