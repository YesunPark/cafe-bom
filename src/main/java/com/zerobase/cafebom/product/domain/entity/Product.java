package com.zerobase.cafebom.product.domain.entity;

import com.zerobase.cafebom.common.BaseTimeEntity;
import com.zerobase.cafebom.productcategory.domain.entity.ProductCategory;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Builder
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
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


    public void modifyProductForm(AdminProductDto productDto, ProductCategory productCategory) {
        if (productDto.getName() != null) {
            this.name = productDto.getName();
        }
        if (productDto.getProductCategoryId() != null) {
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