package com.zerobase.cafebom.product.controller;

import com.zerobase.cafebom.product.controller.form.SoldOutStatusForm;
import com.zerobase.cafebom.product.domain.type.SoldOutStatus;
import com.zerobase.cafebom.product.repository.ProductRepository;
import com.zerobase.cafebom.product.service.ProductService;
import com.zerobase.cafebom.product.service.S3UploaderService;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

import static com.zerobase.cafebom.exception.ErrorCode.SOLD_OUT_FAIL;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;

@Tag(name = "product-controller", description = "관리자 메뉴 CRUD API")
@Controller
@RequestMapping("/admin/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductRepository productRepository;
    private final ProductService productService;
    private final S3UploaderService s3UploaderService;

    @ApiOperation(value = "상품 품절여부 수정(관리자)", notes = "관리자가 상품의 품절여부를 수정합니다.")
    @PatchMapping("/soldOut/{id}")
    public ResponseEntity<?> ProductSoldOutModify(
            @PathVariable Integer id,
            @Valid SoldOutStatusForm form
    ){
        SoldOutStatus soldOutStatus = SoldOutStatus.valueOf(form.getSoldOutStatus());
        boolean result = productService.modifyProductSoldOut(id, form);
        if (result) {
            return new ResponseEntity<>(OK);
        } else {
            return new ResponseEntity<>(SOLD_OUT_FAIL, BAD_REQUEST);
        }
    }
}