package com.zerobase.cafebom.cart.controller;

import com.zerobase.cafebom.cart.controller.form.CartListForm;
import com.zerobase.cafebom.cart.controller.form.CartListForm.Response;
import com.zerobase.cafebom.cart.service.CartService;
import com.zerobase.cafebom.cart.service.dto.CartListDto;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    // wooyoung-23.08.31
    @GetMapping
    public ResponseEntity<List<Response>> cartList(
        @RequestHeader(name = "Authorization") String token) {

        List<CartListDto> cartListDtos = cartService.findCartList(token);

        List<CartListForm.Response> responses = new ArrayList<>();

        for (CartListDto cartListDto : cartListDtos) {
            responses.add(CartListForm.Response.from(cartListDto));
        }

        return ResponseEntity.status(HttpStatus.OK).body(responses);
    }
}
