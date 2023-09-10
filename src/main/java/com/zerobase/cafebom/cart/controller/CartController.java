package com.zerobase.cafebom.cart.controller;

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

@Controller
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @ApiOperation(value = "상품 수량 변경,상품 삭제,장바구니 조회")
    @PostMapping("/cart")
    public ResponseEntity<List<CartProductDto>> cartSave(
        @RequestBody @Valid CartAddForm cartAddForm,
        @RequestHeader(name = "Authorization") String token,
        @RequestParam(value = "Type" ,required = true) String type
    ) {
        return ResponseEntity.ok(cartService.findType(token, cartAddForm, type));
    }
}

