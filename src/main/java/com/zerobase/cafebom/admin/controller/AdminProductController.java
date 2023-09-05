package com.zerobase.cafebom.admin.controller;

import com.zerobase.cafebom.admin.dto.AdminProductDto;
import com.zerobase.cafebom.admin.dto.AdminProductForm;
import com.zerobase.cafebom.admin.service.AdminProductService;
import com.zerobase.cafebom.type.SoldOutStatus;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

@Tag(name = "product-controller", description = "관리자 상품 CRUD API")
@Controller
@RequestMapping("/admin/product")
@RequiredArgsConstructor
//@PreAuthorize("hasRole('ADMIN')")
public class AdminProductController {

    private final AdminProductService adminProductService;

    // jiyeon-23.08.25
    @ApiOperation(value = "상품 전체 조회(관리자)", notes = "관리자가 상품 전체 리스트를 조회합니다")
    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<AdminProductForm.Response>> productList() {
        List<AdminProductForm.Response> productList = adminProductService.findProductList();
        return ResponseEntity.ok().body(productList);
    }

    // jiyeon-23.08.25
    @ApiOperation(value = "상품 Id별 조회(관리자)", notes = "관리자가 상품 Id 별로 조회합니다.")
    @GetMapping("/{id}")
    public ResponseEntity<AdminProductForm.Response> productIdGet(@PathVariable Integer productId) {
        AdminProductForm.Response response = adminProductService.findProductById(productId);
        return ResponseEntity.ok().body(response);
    }

    // jiyeon-23.08.25
    @ApiOperation(value = "상품 등록(관리자)", notes = "관리자가 상품을 등록합니다.")
    @PostMapping(consumes = MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> productAdd(
            @RequestParam(value = "image") MultipartFile image,
            AdminProductForm adminProductForm) throws IOException {
        adminProductService.addProduct(image, AdminProductDto.from(adminProductForm));
        return ResponseEntity.status(CREATED).build();
    }

    // jiyeon-23.08.25
    @ApiOperation(value = "상품 수정(관리자)", notes = "관리자가 상품Id 별로 수정합니다.")
    @PutMapping(value = "/{productId}", consumes = MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> productModify(
            @RequestParam(value = "image") MultipartFile image,
            @PathVariable Integer productId,
            AdminProductForm adminProductForm) throws IOException {
        adminProductService.modifyProduct(image, productId, AdminProductDto.from(adminProductForm));
        return ResponseEntity.status(NO_CONTENT).build();
    }

    // jiyeon-23.08.25
    @ApiOperation(value = "상품 삭제(관리자)", notes = "관리자가 상품Id 별로 삭제합니다.")
    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> productRemove(@PathVariable Integer productId) throws IOException {
        adminProductService.removeProduct(productId);
        return ResponseEntity.status(NO_CONTENT).build();
    }

    // jiyeon-23.08.29
    @ApiOperation(value = "상품 품절여부 수정(관리자)", notes = "관리자가 상품의 품절여부를 수정합니다.")
    @PatchMapping
    public ResponseEntity<Void> productSoldOutModify(
            @RequestParam(name = "productId") Integer productId,
            @RequestParam(name = "soldOutStatus") SoldOutStatus soldOutStatus) {
        adminProductService.modifyProductSoldOut(productId, soldOutStatus);
        return ResponseEntity.status(NO_CONTENT).build();
    }
}