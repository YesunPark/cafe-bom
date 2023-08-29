package com.zerobase.cafebom.product.controller;

import com.zerobase.cafebom.product.controller.form.ProductDetailForm;
import com.zerobase.cafebom.product.service.ProductService;
import com.zerobase.cafebom.product.service.dto.ProductDetailDto;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "product-controller", description = "상품 관련 API")
@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    // wooyoung-23.08.28
    @ApiOperation(value = "상품 상세보기", notes = "상품의 상세 정보를 확인합니다.")
    @GetMapping("/{productId}")
    public ResponseEntity<ProductDetailForm.Response> productDetails(@PathVariable Integer productId) {

        ProductDetailDto productDetails = productService.findProductDetails(productId);

        return ResponseEntity.status(HttpStatus.OK)
            .body(ProductDetailForm.Response.from(productDetails));
    }

}