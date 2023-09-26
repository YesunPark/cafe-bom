package com.zerobase.cafebom.front.order.dto;

import com.zerobase.cafebom.front.order.domain.OrderProduct;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class OrderProductDto {

    public OrderProductDto(OrderProduct orderProduct) {
        this.productName = orderProduct.getProduct().getName();
        this.price = orderProduct.getProduct().getPrice();
    }

    private String productName;

    private Integer price;
}
