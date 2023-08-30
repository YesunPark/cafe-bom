package com.zerobase.cafebom.product.domain.entity;

import com.zerobase.cafebom.common.BaseTimeEntity;
import com.zerobase.cafebom.product.controller.form.ProductForm;
import com.zerobase.cafebom.product.domain.type.SoldOutStatus;
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


    public void modifyProductForm(ProductForm productForm) {
        if (productForm.getName() != null) {
            this.name = productForm.getName();
        }
        if (productForm.getDescription() != null) {
            this.description = productForm.getDescription();
        }
        if (productForm.getPrice() != null) {
            this.price = productForm.getPrice();
        }
        if (productForm.getSoldOutStatus() != null) {
            this.soldOutStatus = productForm.getSoldOutStatus();
        }
    }

    public void modifySoldOutStatus(SoldOutStatus soldOutStatus) {
        this.soldOutStatus = soldOutStatus;
    }

}

