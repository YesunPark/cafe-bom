package com.zerobase.cafebom.product.service;

import com.zerobase.cafebom.exception.CustomException;
import com.zerobase.cafebom.exception.ErrorCode;
import com.zerobase.cafebom.product.domain.entity.Product;
import com.zerobase.cafebom.product.repository.ProductRepository;
import com.zerobase.cafebom.product.service.dto.ProductDto;
import com.zerobase.cafebom.productcategory.repository.ProductCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    private final ProductCategoryRepository productCategoryRepository;

    // 상품 목록 조회-wooyoung-23.08.22
    @Transactional
    public List<ProductDto> findProductList(Integer productCategoryId) {

        if (!productCategoryRepository.existsById(productCategoryId)) {
            throw new CustomException(ErrorCode.PRODUCTCATEGORY_NOT_FOUND);
        }

        List<Product> productList = productRepository.findAllByProductCategoryId(productCategoryId);
        List<ProductDto> productDtoList = new ArrayList<>();

        for (Product product : productList) {
            productDtoList.add(ProductDto.from(product));
        }

        return productDtoList;
    }

}