package com.zerobase.cafebom.admin.service;

import com.zerobase.cafebom.admin.dto.AdminProductForm;
import com.zerobase.cafebom.admin.dto.AdminProductDto;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public interface AdminProductService {

    // 상품 추가-jiyeon-23.08.25
    void addProduct(MultipartFile image, AdminProductDto adminProductDto) throws IOException;

    // 상품 수정-jiyeon-23.08.25
    void modifyProduct(MultipartFile image, Integer id, AdminProductDto adminProductDto)
        throws IOException;

    // 상품 삭제-jiyeon-23.08.25
    void removeProduct(Integer id);

    // 상품 전체 조회-jiyeon-23.08.31
    List<AdminProductForm.Response> findProductList();

    // 상품Id별 조회-jiyeon-23.08.31
    AdminProductForm.Response findProductById(Integer id);
}
