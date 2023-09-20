package com.zerobase.cafebom.front.cart.dto;

import com.zerobase.cafebom.front.cart.domain.Cart;
import com.zerobase.cafebom.front.product.domain.Product;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class CartProductDto {

    private Product product;

    private Integer quantity;

    private List<Integer> optionIdList;

    public void addOptionId(Integer optionId) {
        optionIdList.add(optionId);
    }

    public static CartProductDto from(Cart cart) {
        return CartProductDto.builder()
            .product(cart.getProduct())
            .quantity(cart.getQuantity())
            .optionIdList(new ArrayList<>())
            .build();
    }
}
