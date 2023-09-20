package com.zerobase.cafebom.front.order.dto;

import com.zerobase.cafebom.front.order.domain.OrdersProduct;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class OrdersProductDto {

    public OrdersProductDto(OrdersProduct ordersProduct) {
        this.productName = ordersProduct.getProduct().getName();
        this.price = ordersProduct.getProduct().getPrice();
    }

    private String productName;

    private Integer price;
}
