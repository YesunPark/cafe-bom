package com.zerobase.cafebom.cart.controller;

import com.zerobase.cafebom.cart.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

//    // wooyoung-23.08.31
//    @GetMapping
//    public ResponseEntity<List<CartListForm.Response>> cartList(@RequestHeader(name = "Authorization") String token) {
//
//        cartService.findCartList(token);
//
//        return ResponseEntity.status(HttpStatus.OK).build();
//    }
}
