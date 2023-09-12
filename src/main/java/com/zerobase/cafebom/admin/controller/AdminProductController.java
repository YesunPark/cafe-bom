package com.zerobase.cafebom.admin.controller;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

import com.zerobase.cafebom.admin.dto.AdminProductDto;
import com.zerobase.cafebom.admin.dto.AdminProductForm;
import com.zerobase.cafebom.admin.service.AdminProductService;
import com.zerobase.cafebom.type.SoldOutStatus;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "admin-product-controller", description = "관리자 상품 CRUD API")
@Controller
@RequestMapping("/admin/product")
@RequiredArgsConstructor
public class AdminProductController {

    private final AdminProductService adminProductService;

    // jiyeon-23.09.05
    @ApiOperation(value = "상품 전체 목록 조회", notes = "관리자가 전체 상품 목록을 조회합니다.")
    @GetMapping(value = "/list", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<?> productList() {
        List<AdminProductForm.Response> productList = adminProductService.findProductList();
        return ResponseEntity.ok().body(productList);
    }

    // jiyeon-23.09.05
    @ApiOperation(value = "상품 단건 조회", notes = "관리자가 하나의 상품에 대한 정보를 조회합니다.")
    @GetMapping("/{productId}")
    public ResponseEntity<?> productIdGet(@PathVariable Integer productId) {
        AdminProductForm.Response response = adminProductService.findProductById(productId);
        return ResponseEntity.ok().body(response);
    }

    // jiyeon-23.09.05
    @ApiOperation(value = "상품 등록", notes = "관리자가 상품을 등록합니다.")
    @PostMapping(consumes = MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> productAdd(
        @RequestParam(value = "image") MultipartFile image,
        AdminProductForm adminProductForm) throws IOException {
        adminProductService.addProduct(image, AdminProductDto.from(adminProductForm));
        return ResponseEntity.status(CREATED).build();
    }

    // jiyeon-23.09.05
    @ApiOperation(value = "상품 수정", notes = "관리자가 상품 정보를 수정합니다.")
    @PutMapping(value = "/{productId}", consumes = MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> productModify(
        @RequestParam(value = "image") MultipartFile image,
        @PathVariable Integer productId,
        AdminProductForm adminProductForm) throws IOException {
        adminProductService.modifyProduct(image, productId, AdminProductDto.from(adminProductForm));
        return ResponseEntity.status(NO_CONTENT).build();
    }

    // jiyeon-23.09.05
    @ApiOperation(value = "상품 삭제", notes = "관리자가 상품을 삭제합니다.")
    @DeleteMapping("/{productId}")
    public ResponseEntity<?> productRemove(@PathVariable Integer productId) throws IOException {
        adminProductService.removeProduct(productId);
        return ResponseEntity.status(NO_CONTENT).build();
    }

    // jiyeon-23.08.29
    @ApiOperation(value = "상품 품절여부 수정", notes = "관리자가 상품의 품절여부를 수정합니다.")
    @PatchMapping
    public ResponseEntity<Void> productSoldOutModify(
        @RequestParam(name = "productId") Integer productId,
        @RequestParam(name = "soldOutStatus") SoldOutStatus soldOutStatus) {
        adminProductService.modifyProductSoldOut(productId, soldOutStatus);
        return ResponseEntity.status(NO_CONTENT).build();
    }
}