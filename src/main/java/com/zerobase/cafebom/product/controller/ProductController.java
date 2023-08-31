package com.zerobase.cafebom.product.controller;

import com.zerobase.cafebom.product.controller.form.SoldOutStatusForm;
import com.zerobase.cafebom.product.service.ProductService;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Tag(name = "product-controller", description = "관리자 메뉴 CRUD API")
@Controller
@RequestMapping("/admin/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @ApiOperation(value = "상품 품절여부 수정(관리자)", notes = "관리자가 상품의 품절여부를 수정합니다.")
    @PatchMapping("/product-status/{id}")
    public ResponseEntity<?> productSoldOutModify(
            @PathVariable Integer id,
            @Valid SoldOutStatusForm form){
        productService.modifyProductSoldOut(id,form);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}