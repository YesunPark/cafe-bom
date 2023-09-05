package com.zerobase.cafebom.admin.controller;

import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

import com.zerobase.cafebom.admin.dto.AdminProductDto;
import com.zerobase.cafebom.admin.dto.AdminProductForm;
import com.zerobase.cafebom.admin.service.AdminProductService;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "product-controller", description = "관리자 메뉴 CRUD API")
@Controller
@RequestMapping("/admin/product")
@RequiredArgsConstructor
//@PreAuthorize("hasRole('ADMIN')")
public class AdminProductController {

    private final AdminProductService adminProductService;

    // jiyeon-23.08.25
    @ApiOperation(value = "상품 전체 조회(관리자)", notes = "관리자가 상품 전체 리스트를 조회합니다")
    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<?> productList() {
        List<AdminProductForm.Response> productList = adminProductService.findProductList();
        return ResponseEntity.ok().body(productList);
    }

    // jiyeon-23.08.25
    @ApiOperation(value = "상품 Id별 조회(관리자)", notes = "관리자가 상품 Id 별로 조회합니다.")
    @GetMapping("/{id}")
    public ResponseEntity<?> productIdGet(@PathVariable Integer id) {
        AdminProductForm.Response response = adminProductService.findProductById(id);
        return ResponseEntity.ok().body(response);
    }

    // jiyeon-23.08.25
    @ApiOperation(value = "상품 등록(관리자)", notes = "관리자가 상품을 등록합니다.")
    @PostMapping(consumes = MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> productAdd(
        HttpServletRequest request,
        @RequestParam(value = "image") MultipartFile image,
        AdminProductForm adminProductForm) throws IOException {
        adminProductService.addProduct(image, AdminProductDto.from(adminProductForm));
        return ResponseEntity.status(NO_CONTENT).build();
    }

    // jiyeon-23.08.25
    @ApiOperation(value = "상품 수정(관리자)", notes = "관리자가 상품Id 별로 수정합니다.")
    @PutMapping(value = "/{id}", consumes = MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> productModify(
        @RequestParam(value = "image") MultipartFile image,
        @PathVariable Integer id,
        AdminProductForm adminProductForm) throws IOException {
        adminProductService.modifyProduct(image, id, AdminProductDto.from(adminProductForm));
        return ResponseEntity.status(NO_CONTENT).build();
    }

    // jiyeon-23.08.25
    @ApiOperation(value = "상품 삭제(관리자)", notes = "관리자가 상품Id 별로 삭제합니다.")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> productRemove(@PathVariable Integer id) {
        adminProductService.removeProduct(id);
        return ResponseEntity.status(NO_CONTENT).build();
    }
}