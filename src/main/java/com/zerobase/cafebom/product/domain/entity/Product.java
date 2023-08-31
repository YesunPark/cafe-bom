package com.zerobase.cafebom.product.domain.entity;

import com.zerobase.cafebom.common.BaseTimeEntity;
import com.zerobase.cafebom.product.service.dto.ProductDto;
import com.zerobase.cafebom.productcategory.domain.entity.ProductCategory;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Builder(toBuilder = true)
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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
    @Enumerated(EnumType.STRING)
    private SoldOutStatus soldOutStatus;

    @NotNull
    private String picture;


    public void modifyProductForm(ProductDto productDto, ProductCategory productCategory) {
        if (productDto.getName() != null) {
            this.name = productDto.getName();
        }
        if (productDto.getProductCategory() != null) {
            this.productCategory = productCategory;
        }
        if (productDto.getDescription() != null) {
            this.description = productDto.getDescription();
        }
        if (productDto.getPrice() != null) {
            this.price = productDto.getPrice();
        }
        if (productDto.getSoldOutStatus() != null) {
            this.soldOutStatus = productDto.getSoldOutStatus();
        }
    }

}

