package com.zerobase.cafebom.cart.service;

import com.zerobase.cafebom.cart.repository.CartRepository;
import com.zerobase.cafebom.cartoption.repository.CartOptionRepository;
import com.zerobase.cafebom.option.repository.OptionRepository;
import com.zerobase.cafebom.security.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;

    private final CartOptionRepository cartOptionRepository;

    private final OptionRepository optionRepository;
    
    private final TokenProvider tokenProvider;

//    public List<CartListDto> findCartList(String token) {
//        Long memberId = tokenProvider.getId(token);
//
//        List<Cart> cartList = cartRepository.findAllByMember(memberId);
//
//        cartList.forEach(cart -> );
}
