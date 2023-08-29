package com.zerobase.cafebom.orders.service.dto;

import com.zerobase.cafebom.ordersproduct.domain.entity.OrdersProduct;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Getter
@Builder
@AllArgsConstructor
public class OrdersProductDto {




    public OrdersProductDto (OrdersProduct ordersProduct)
    {

        this.productName = ordersProduct.getProduct().getName();

        this.price = ordersProduct.getProduct().getPrice();

    }


    private String productName;


    private Integer price;
}
