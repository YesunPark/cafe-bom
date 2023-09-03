package com.zerobase.cafebom.product.service;

import static com.zerobase.cafebom.exception.ErrorCode.PRODUCT_NOT_FOUND;

import com.zerobase.cafebom.exception.CustomException;
import com.zerobase.cafebom.exception.ErrorCode;
import com.zerobase.cafebom.option.domain.entity.Option;
import com.zerobase.cafebom.option.repository.OptionRepository;
import com.zerobase.cafebom.product.domain.entity.Product;
import com.zerobase.cafebom.product.repository.ProductRepository;
import com.zerobase.cafebom.product.service.dto.ProductDetailDto;
import com.zerobase.cafebom.product.service.dto.ProductDto;
import com.zerobase.cafebom.productcategory.repository.ProductCategoryRepository;
import com.zerobase.cafebom.productoptioncategory.domain.entity.ProductOptionCategory;
import com.zerobase.cafebom.productoptioncategory.repository.ProductOptionCategoryRepository;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    private final ProductOptionCategoryRepository productOptionCategoryRepository;

    private final OptionRepository optionRepository;

    // 상품 상세 정보 조회-wooyoung-23.08.28
    public ProductDetailDto findProductDetails(Integer productId) {

        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new CustomException(PRODUCT_NOT_FOUND));

        List<ProductOptionCategory> allByProductId =
            productOptionCategoryRepository.findAllByProductId(productId);

        Map<ProductOptionCategory, List<Option>> productOptionList = new HashMap<>();

        for (ProductOptionCategory productOptionCategory : allByProductId) {

            List<Option> optionList = optionRepository.findAllByOptionCategoryId(
                productOptionCategory.getOptionCategory());

            productOptionList.put(productOptionCategory, optionList);
        }

        return ProductDetailDto.from(product, productOptionList);
    }
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