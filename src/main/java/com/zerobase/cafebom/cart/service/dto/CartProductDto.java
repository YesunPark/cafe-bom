package com.zerobase.cafebom.cart.service.dto;

import com.zerobase.cafebom.cart.domain.entity.Cart;
import com.zerobase.cafebom.cartoption.domain.entity.CartOption;
import com.zerobase.cafebom.option.domain.entity.Option;
import com.zerobase.cafebom.orders.service.dto.OrdersProductDto;
import com.zerobase.cafebom.product.domain.entity.Product;
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

    private Integer count;

    private List<Integer> optionIdList;

    public void addOptionId(Integer optionId) {
        optionIdList.add(optionId);
    }

    public static CartProductDto from(Cart cart) {
        return CartProductDto.builder()
            .product(cart.getProduct())
            .count(cart.getCount())
            .optionIdList(new ArrayList<>())
            .build();

    }
}
