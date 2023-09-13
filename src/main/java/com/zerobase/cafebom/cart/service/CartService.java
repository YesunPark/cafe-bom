package com.zerobase.cafebom.cart.service;


import static com.zerobase.cafebom.exception.ErrorCode.MEMBER_NOT_EXISTS;
import static com.zerobase.cafebom.type.CartOrderStatus.BEFORE_ORDER;

import com.zerobase.cafebom.cart.domain.Cart;
import com.zerobase.cafebom.cart.domain.CartRepository;
import com.zerobase.cafebom.cart.dto.CartListDto;
import com.zerobase.cafebom.cart.dto.CartListOptionDto;
import com.zerobase.cafebom.cartoption.domain.CartOption;
import com.zerobase.cafebom.cartoption.domain.CartOptionRepository;
import com.zerobase.cafebom.exception.CustomException;
import com.zerobase.cafebom.member.domain.MemberRepository;
import com.zerobase.cafebom.security.TokenProvider;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartService {

    private final MemberRepository memberRepository;

    private final CartRepository cartRepository;

    private final CartOptionRepository cartOptionRepository;

    private final TokenProvider tokenProvider;

    // 장바구니 목록 조회-wooyoung-23.09.14
    public List<CartListDto> findCartList(String token) {
        Long memberId = tokenProvider.getId(token);

        memberRepository.findById(memberId)
            .orElseThrow(() -> new CustomException(MEMBER_NOT_EXISTS));

        List<Cart> cartList = cartRepository.findAllByMemberAndStatus(memberId, BEFORE_ORDER);

        List<CartListDto> cartListDtoList = new ArrayList<>();

        for (Cart cart : cartList) {
            List<CartOption> allByCart = cartOptionRepository.findAllByCart(cart);

            List<CartListOptionDto> cartListOptionDtos = new ArrayList<>();

            for (CartOption cartOption : allByCart) {
                cartListOptionDtos.add(CartListOptionDto.from(cartOption.getOption()));
            }

            cartListDtoList.add(CartListDto.from(cart, cartListOptionDtos));
        }

        return cartListDtoList;
    }
}
