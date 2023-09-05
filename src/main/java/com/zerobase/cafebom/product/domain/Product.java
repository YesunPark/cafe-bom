package com.zerobase.cafebom.product.domain;

import com.zerobase.cafebom.admin.dto.AdminProductDto;
import com.zerobase.cafebom.common.BaseTimeEntity;
import com.zerobase.cafebom.productcategory.domain.ProductCategory;
import com.zerobase.cafebom.type.SoldOutStatus;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Builder(toBuilder = true)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    public void modifyNewImageUrl(String newImageUrl) {
        this.picture = newImageUrl;
    }
}