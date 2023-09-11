package com.zerobase.cafebom.cart.controller;

import static org.springframework.http.HttpStatus.OK;

import com.zerobase.cafebom.cart.dto.CartListForm;
import com.zerobase.cafebom.cart.service.CartService;
import com.zerobase.cafebom.cart.dto.CartListDto;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zerobase.cafebom.cart.controller.form.CartAddForm;
import com.zerobase.cafebom.cart.service.CartService;
import com.zerobase.cafebom.cart.service.dto.CartProductDto;
import com.zerobase.cafebom.security.TokenProvider;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;


@Tag(name = "cart-controller", description = "장바구니 관련 API")
@RestController
@RequestMapping("/auth/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    // wooyoung-23.08.31
    @ApiOperation(value = "장바구니 목록 조회", notes = "사용자가 장바구니 목록을 조회합니다.")
    @GetMapping
    public ResponseEntity<List<CartListForm.Response>> cartList(
        @RequestHeader(name = "Authorization") String token) {

        List<CartListDto> cartListDtos = cartService.findCartList(token);

        List<CartListForm.Response> responses = new ArrayList<>();

        for (CartListDto cartListDto : cartListDtos) {
            responses.add(CartListForm.Response.from(cartListDto));
        }

        return ResponseEntity.status(OK).body(responses);
    }

    // youngseon-23.09.11
    @ApiOperation(value = "상품 수량 변경")
    @PostMapping
    public ResponseEntity<List<CartProductDto>> cartModify(
        @RequestBody @Valid CartAddForm cartAddForm,
        @RequestHeader(name = "Authorization") String token
    ) {
        return ResponseEntity.ok(cartService.modifyCart(token, cartAddForm));
    }

    //youngseon-23.09.11
    @ApiOperation(value = "상품 삭제")
    @DeleteMapping
    public ResponseEntity<List<CartProductDto>> cartRemove(
        @RequestBody @Valid CartAddForm cartAddForm,
        @RequestHeader(name = "Authorization") String token
    ) {
        return ResponseEntity.ok(cartService.removeCart(token, cartAddForm));
    }

    //youngseon-23.09.11
    @ApiOperation(value = "장바구니 조회")
    @GetMapping("{cartId}")
    public ResponseEntity<List<CartProductDto>> cartList(
        @PathVariable Long cartId,
        @RequestBody @Valid CartAddForm cartAddForm,
        @RequestHeader(name = "Authorization") String token
    ) {
        return ResponseEntity.ok(cartService.findCart(token,cartId));
    }
}

