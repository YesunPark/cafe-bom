package com.zerobase.cafebom.front.cart.dto;

import com.zerobase.cafebom.front.cart.domain.Cart;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class CartListDto {

    private Long cartId;

    private Integer productId;

    private String productName;

    private String productPicture;

    private List<CartListOptionDto> cartListOptionDtos;

    private Integer quantity;

    public static CartListDto from(Cart cart, List<CartListOptionDto> cartListOptionDtos) {
        return CartListDto.builder()
            .cartId(cart.getId())
            .productId(cart.getProduct().getId())
            .productName(cart.getProduct().getName())
            .productPicture(cart.getProduct().getPicture())
            .cartListOptionDtos(cartListOptionDtos)
            .quantity(cart.getQuantity())
            .build();
    }
}
