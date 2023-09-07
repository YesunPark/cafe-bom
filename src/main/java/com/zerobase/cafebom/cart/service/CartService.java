package com.zerobase.cafebom.cart.service;


import static com.zerobase.cafebom.type.CartOrderStatus.BEFORE_ORDER;

import com.zerobase.cafebom.cart.domain.Cart;
import com.zerobase.cafebom.cart.domain.CartRepository;
import com.zerobase.cafebom.cart.dto.CartListDto;
import com.zerobase.cafebom.cartoption.domain.CartOption;
import com.zerobase.cafebom.cartoption.domain.CartOptionRepository;
import com.zerobase.cafebom.option.domain.Option;
import com.zerobase.cafebom.product.domain.Product;
import com.zerobase.cafebom.security.TokenProvider;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;

    private final CartOptionRepository cartOptionRepository;

    private final TokenProvider tokenProvider;

    // 멤버 id를 통해 주문 전의 장바구니 목록을 추출-wooyoung-23.09.03
    public List<CartListDto> findCartList(String token) {
        Long memberId = tokenProvider.getId(token);

        List<Cart> cartList = cartRepository.findAllByMemberAndStatus(memberId, BEFORE_ORDER);

        List<CartListDto> cartListDtoList = new ArrayList<>();

        for (Cart cart : cartList) {
            List<CartOption> allByCart = cartOptionRepository.findAllByCart(cart);

            List<Option> optionList = new ArrayList<>();

            for (CartOption cartOption : allByCart) {
                optionList.add(cartOption.getOption());
            }

            Product product = cart.getProduct();

            CartListDto cartListDto = CartListDto.builder()
                .productId(product.getId())
                .productName(product.getName())
                .productPicture(product.getPicture())
                .productOptions(optionList)
                .productCount(cart.getProductCount())
                .build();

            cartListDtoList.add(cartListDto);
        }

        return cartListDtoList;
    }
}
