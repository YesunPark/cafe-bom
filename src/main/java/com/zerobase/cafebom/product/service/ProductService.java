package com.zerobase.cafebom.product.service;

import com.zerobase.cafebom.product.service.dto.ProductDto;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public interface ProductService {

    // 상품 추가-jiyeon-23.08.25
    void addProduct(MultipartFile image, ProductDto productDto) throws IOException;

    // 상품 수정-jiyeon-23.08.25
    void modifyProduct(MultipartFile image, Integer id, ProductDto productDto) throws IOException;

    // 상품 삭제-jiyeon-23.08.25
    void removeProduct(Integer id);


}
