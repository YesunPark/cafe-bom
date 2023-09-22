package com.zerobase.cafebom.admin.product.controller;

import com.zerobase.cafebom.admin.product.dto.AdminProductDto;
import com.zerobase.cafebom.admin.product.dto.AdminProductForm;
import com.zerobase.cafebom.admin.product.service.AdminProductService;
import com.zerobase.cafebom.common.type.SoldOutStatus;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

@Tag(name = "admin-product-controller", description = "관리자 상품 CRUD API")
@Controller
@PreAuthorize("hasRole('ROLE_ADMIN')")
@RequestMapping("/admin/product")
@RequiredArgsConstructor
public class AdminProductController {

    private final AdminProductService adminProductService;

    // jiyeon-23.09.13
    @ApiOperation(value = "상품 전체 목록 조회", notes = "관리자가 전체 상품 목록을 조회합니다.")
    @GetMapping(value = "/list", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<AdminProductForm.Response>> productList() {
        List<AdminProductForm.Response> productList = adminProductService.findProductList();
        return ResponseEntity.ok().body(productList);
    }

    // jiyeon-23.09.13
    @ApiOperation(value = "상품 단건 조회", notes = "관리자가 하나의 상품에 대한 정보를 조회합니다.")
    @GetMapping("/{productId}")
    public ResponseEntity<AdminProductForm.Response> productIdGet(@PathVariable Integer productId) {
        AdminProductForm.Response response = adminProductService.findProductById(productId);
        return ResponseEntity.ok().body(response);
    }

    // jiyeon-23.09.22
    @ApiOperation(value = "상품 등록", notes = "관리자가 상품을 등록합니다.")
    @PostMapping(consumes = MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> productAdd(
        @RequestParam(value = "image") MultipartFile image,
        @Valid AdminProductForm adminProductForm) throws IOException {
        adminProductService.addProduct(image, AdminProductDto.from(adminProductForm));
        return ResponseEntity.status(CREATED).build();
    }

    // jiyeon-23.09.13
    @ApiOperation(value = "상품 수정", notes = "관리자가 상품 정보를 수정합니다.")
    @PutMapping(value = "/{productId}", consumes = MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> productModify(
        @RequestParam(value = "image") MultipartFile image,
        @PathVariable Integer productId,
        AdminProductForm adminProductForm) throws IOException {
        adminProductService.modifyProduct(image, productId, AdminProductDto.from(adminProductForm));
        return ResponseEntity.status(NO_CONTENT).build();
    }

    // jiyeon-23.09.13
    @ApiOperation(value = "상품 삭제", notes = "관리자가 상품을 삭제합니다.")
    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> productRemove(@PathVariable Integer productId) throws IOException {
        adminProductService.removeProduct(productId);
        return ResponseEntity.status(NO_CONTENT).build();
    }

    // jiyeon-23.09.13
    @ApiOperation(value = "상품 품절여부 수정", notes = "관리자가 상품의 품절여부를 수정합니다.")
    @PatchMapping
    public ResponseEntity<Void> productSoldOutModify(
        @RequestParam(name = "productId") Integer productId,
        @RequestParam(name = "soldOutStatus") SoldOutStatus soldOutStatus) {
        adminProductService.modifyProductSoldOut(productId, soldOutStatus);
        return ResponseEntity.status(NO_CONTENT).build();
    }
}