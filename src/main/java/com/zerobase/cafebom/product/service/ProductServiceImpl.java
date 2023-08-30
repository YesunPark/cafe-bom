package com.zerobase.cafebom.product.service;


import com.zerobase.cafebom.member.repository.MemberRepository;
import com.zerobase.cafebom.product.controller.form.SoldOutStatusForm;
import com.zerobase.cafebom.product.domain.entity.Product;
import com.zerobase.cafebom.product.domain.type.SoldOutStatus;
import com.zerobase.cafebom.product.repository.ProductRepository;
import com.zerobase.cafebom.productcategory.repository.ProductCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductCategoryRepository productCategoryRepository;
    private final S3UploaderService s3UploaderService;
    private final MemberRepository memberRepository;

    // 관리자 상품 품절여부 수정-jiyeon-23.08.29
    @Override
    @Transactional
    public boolean modifyProductSoldOut(Integer id, SoldOutStatusForm form) {
        Optional<Product> productId = productRepository.findById(id);
        SoldOutStatus soldOutStatus = SoldOutStatus.valueOf(form.getSoldOutStatus());
        if (productId.isPresent()) {
            Product product = productId.get();
            product.modifySoldOutStatus(soldOutStatus);
            productRepository.save(product);
            return true;
        } else {
            return false;
        }
    }

}
