package com.zerobase.CafeBom.cart;


import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "cart")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Menu menu;

    @OneToOne
    private User user;

    @OneToOne
    private Option option;

    @Column(nullable = false)
    private Integer count;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public static Cart createCart(User user)
    {
        return  Cart.builder()
                .user(user)
                .count(0)
                .createdAt(LocalDateTime.now())
                .build();
    }

}
