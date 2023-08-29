package com.zerobase.cafebom.product.service;

import com.zerobase.cafebom.product.controller.form.ProductForm;
import com.zerobase.cafebom.product.service.dto.ProductDto;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public interface ProductService {

    // 상품 추가-jiyeon-23.08.25
    ProductDto addProduct(MultipartFile image, ProductForm productForm) throws IOException;

    // 상품 삭제-jiyeon-23.08.25
    boolean removeProduct(Integer id);

    // 상품 수정-jiyeon-23.08.25
    boolean modifyProduct(MultipartFile image, Integer id, ProductForm productForm) throws IOException;

}
