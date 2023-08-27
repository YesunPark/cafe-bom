package com.zerobase.cafebom.product.controller;

import com.zerobase.cafebom.product.controller.form.ProductForm;
import com.zerobase.cafebom.product.domain.entity.Product;
import com.zerobase.cafebom.product.repository.ProductRepository;
import com.zerobase.cafebom.product.service.ProductService;
import com.zerobase.cafebom.product.service.S3UploaderService;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static com.zerobase.cafebom.exception.ErrorCode.*;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

@Tag(name = "product-controller", description = "관리자 메뉴 CRUD API")
@Controller
@RequestMapping("/admin/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductRepository productRepository;
    private final ProductService productService;
    private final S3UploaderService s3UploaderService;

    // jiyeon-23.08.25
    @ApiOperation(value = "관리자 메뉴 전체조회", notes = "전체 리스트를 조회합니다.")
    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public List<Product> ProductList() {
        List<Product> products = productRepository.findAll();
        return products;
    }

    // jiyeon-23.08.25
    @ApiOperation(value = "관리자 상품 Id별 조회", notes = "상품 Id 별로 조회합니다.")
    @GetMapping("/{id}")
    public ResponseEntity<Product> ProductIdGet(@PathVariable Integer id) {
        Optional<Product> product = productRepository.findById(id);

        if (product.isPresent()) {
            return new ResponseEntity<>(product.get(), OK);
        } else {
            return new ResponseEntity<>(NOT_FOUND);
        }
    }

    // jiyeon-23.08.25
    @ApiOperation(value = "관리자 상품 등록", notes = "관리자가 상품을 등록합니다.")
    @PostMapping(value = "/add", consumes = MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> ProductAdd(
            @RequestParam(value = "image") MultipartFile image,
            ProductForm productForm) {
        try {
            productService.addProduct(image, productForm);
            return new ResponseEntity<>(CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(PRODUCT_NOT_EXISTS, INTERNAL_SERVER_ERROR);
        }
    }

    // jiyeon-23.08.25
    @ApiOperation(value = "관리자 상품 수정", notes = "관리자가 상품Id 별로 수정합니다.")
    @PutMapping(value = "/update/{id}", consumes = MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> ProductUpdate(
            @RequestParam(value = "image") MultipartFile image,
            @PathVariable Integer id,
            ProductForm productForm) throws IOException {
        boolean isUpdated = productService.updateProduct(image, id, productForm);
        if (isUpdated) {
            return new ResponseEntity<>(OK);
        } else {
            return new ResponseEntity<>(PRODUCT_UPDATE_FAIL, BAD_REQUEST);

        }
    }

    // jiyeon-23.08.25
    @ApiOperation(value = "관리자 상품 삭제", notes = "관리자가 상품Id 별로 삭제합니다.")
    @DeleteMapping("/remove/{id}")
    public ResponseEntity<?> ProductRemove(@PathVariable Integer id) {
        boolean removeProduct = productService.removeProduct(id);
        if (removeProduct) {
            return new ResponseEntity<>(OK);
        } else {
            return new ResponseEntity<>(PRODUCT_REMOVE_FAIL, NOT_FOUND);
        }
    }



}