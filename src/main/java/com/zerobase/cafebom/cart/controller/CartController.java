package com.zerobase.cafebom.cart.controller;

import static org.springframework.http.HttpStatus.OK;

import com.zerobase.cafebom.cart.controller.form.CartListForm;
import com.zerobase.cafebom.cart.controller.form.CartListForm.Response;
import com.zerobase.cafebom.cart.service.CartService;
import com.zerobase.cafebom.cart.service.dto.CartListDto;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "cart-controller", description = "장바구니 관련 API")
@RestController
@RequestMapping("/auth/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    // wooyoung-23.08.31
    @ApiOperation(value = "사용자의 장바구니 목록 조회", notes = "사용자가 장바구니 목록을 조회합니다.")
    @GetMapping
    public ResponseEntity<List<Response>> cartList(
        @RequestHeader(name = "Authorization") String token) {

        List<CartListDto> cartListDtos = cartService.findCartList(token);

        List<CartListForm.Response> responses = new ArrayList<>();

        for (CartListDto cartListDto : cartListDtos) {
            responses.add(CartListForm.Response.from(cartListDto));
        }

        return ResponseEntity.status(OK).body(responses);
    }
}
