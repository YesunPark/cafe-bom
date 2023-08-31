package com.zerobase.cafebom.product.service;


import com.zerobase.cafebom.exception.CustomException;
import com.zerobase.cafebom.exception.ErrorCode;
import com.zerobase.cafebom.product.controller.form.SoldOutStatusForm;
import com.zerobase.cafebom.product.domain.entity.Product;
import com.zerobase.cafebom.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    // 관리자 상품 품절여부 수정-jiyeon-23.08.29
    @Override
    @Transactional
    public void modifyProductSoldOut(Integer id, SoldOutStatusForm form) {
        Product productId = productRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.PRODUCT_NOT_EXISTS));

        productId.modifySoldOutStatus(form.getSoldOutStatus());
        productRepository.save(productId);

    }
}
