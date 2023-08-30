package com.zerobase.cafebom.product.service;

import com.zerobase.cafebom.product.controller.form.SoldOutStatusForm;
import org.springframework.stereotype.Service;

@Service
public interface ProductService {
    // 상품 품절여부 수정-jiyeon-23.08.29
    void modifyProductSoldOut(Integer id, SoldOutStatusForm form);
}
