package com.zerobase.CafeBom.menu.domain.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private int categoryId;

    @NotNull
    @Column(unique = true)
    private String menuName;

    private String description;

    @NotNull
    private int price;

    @NotNull
    private byte[] menuPicture;

    @NotNull
    private LocalDateTime createdDate;

    private LocalDateTime modifiedDate;

}
