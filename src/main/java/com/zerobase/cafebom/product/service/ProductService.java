package com.zerobase.cafebom.product.service;

import com.zerobase.cafebom.product.controller.form.ProductForm;
import com.zerobase.cafebom.product.service.dto.ProductDto;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public interface ProductService {

    // 상품 추가-jiyeon-23.08.25
    void addProduct(MultipartFile image, ProductDto productDto) throws IOException;

    // 상품 수정-jiyeon-23.08.25
    void modifyProduct(MultipartFile image, Integer id, ProductDto productDto) throws IOException;

    // 상품 삭제-jiyeon-23.08.25
    void removeProduct(Integer id);
    
    // 상품 전체 조회-jiyeon-23.08.31
    List<ProductForm.Response> findProductList();

    ProductForm.Response findProductById(Integer id);
}
