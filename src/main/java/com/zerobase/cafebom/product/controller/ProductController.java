package com.zerobase.cafebom.product.controller;

import com.zerobase.cafebom.product.controller.form.ProductForm;
import com.zerobase.cafebom.product.domain.entity.Product;
import com.zerobase.cafebom.product.repository.ProductRepository;
import com.zerobase.cafebom.product.service.ProductService;
import com.zerobase.cafebom.product.service.S3UploaderService;
import com.zerobase.cafebom.product.service.dto.ProductDto;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

@Tag(name = "product-controller", description = "관리자 메뉴 CRUD API")
@Controller
@RequestMapping("/admin/product")
@RequiredArgsConstructor
//@PreAuthorize("hasRole('ADMIN')")
public class ProductController {

    private final ProductRepository productRepository;
    private final ProductService productService;
    private final S3UploaderService s3UploaderService;

    // jiyeon-23.08.25
    @ApiOperation(value = "상품 전체 조회(관리자)", notes = "관리자가 상품 전체 리스트를 조회합니다")
    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<?> ProductList() {
        List<Product> products = productRepository.findAll();
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(products);
    }

    // jiyeon-23.08.25
    @ApiOperation(value = "상품 Id별 조회(관리자)", notes = "관리자가 상품 Id 별로 조회합니다.")
    @GetMapping("/{id}")
    public ResponseEntity<?> ProductIdGet(@PathVariable Integer id) {
        Optional<Product> product = productRepository.findById(id);

        if (product.isPresent()) {
            return new ResponseEntity<>(OK);
        } else {
            return new ResponseEntity<>(NOT_FOUND);
        }
    }

    // jiyeon-23.08.25

    @ApiOperation(value = "상품 등록(관리자)", notes = "관리자가 상품을 등록합니다.")
    @PostMapping(consumes = MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> ProductAdd(
            HttpServletRequest request,
            @RequestParam(value = "image") MultipartFile image,
            ProductForm productForm) throws IOException {
        productService.addProduct(image, ProductDto.from(productForm));
        return ResponseEntity.status(NO_CONTENT).build();
    }

    // jiyeon-23.08.25
    @ApiOperation(value = "상품 수정(관리자)", notes = "관리자가 상품Id 별로 수정합니다.")
    @PutMapping(value = "/{id}", consumes = MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> ProductModify(
            @RequestParam(value = "image") MultipartFile image,
            @PathVariable Integer id,
            ProductForm productForm) throws IOException {
        productService.modifyProduct(image, id, ProductDto.from(productForm));
        return ResponseEntity.status(NO_CONTENT).build();
    }

    // jiyeon-23.08.25
    @ApiOperation(value = "상품 삭제(관리자)", notes = "관리자가 상품Id 별로 삭제합니다.")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> ProductRemove(@PathVariable Integer id) {
        productService.removeProduct(id);
        return ResponseEntity.status(NO_CONTENT).build();
    }
}