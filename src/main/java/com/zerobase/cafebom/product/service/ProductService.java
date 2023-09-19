package com.zerobase.cafebom.product.service;

import static com.zerobase.cafebom.exception.ErrorCode.BEST_PRODUCT_NOT_EXISTS;
import static com.zerobase.cafebom.exception.ErrorCode.PRODUCTCATEGORY_NOT_EXISTS;
import static com.zerobase.cafebom.exception.ErrorCode.PRODUCT_NOT_EXISTS;
import static com.zerobase.cafebom.type.OrdersReceiptStatus.RECEIVED;

import static com.zerobase.cafebom.exception.ErrorCode.BEST_PRODUCT_NOT_EXISTS;
import static com.zerobase.cafebom.exception.ErrorCode.PRODUCT_NOT_EXISTS;
import static com.zerobase.cafebom.type.OrdersReceiptStatus.RECEIVED;

import com.zerobase.cafebom.exception.CustomException;
import com.zerobase.cafebom.option.domain.Option;
import com.zerobase.cafebom.option.domain.OptionRepository;
import com.zerobase.cafebom.orders.domain.Orders;
import com.zerobase.cafebom.orders.domain.OrdersRepository;
import com.zerobase.cafebom.ordersproduct.domain.OrdersProduct;
import com.zerobase.cafebom.ordersproduct.domain.OrdersProductRepository;
import com.zerobase.cafebom.product.domain.Product;
import com.zerobase.cafebom.product.domain.ProductRepository;
import com.zerobase.cafebom.product.dto.BestProductDto;
import com.zerobase.cafebom.product.dto.ProductDetailDto;
import com.zerobase.cafebom.product.dto.ProductDto;
import com.zerobase.cafebom.productcategory.domain.ProductCategoryRepository;
import com.zerobase.cafebom.productoptioncategory.domain.ProductOptionCategory;
import com.zerobase.cafebom.productoptioncategory.domain.ProductOptionCategoryRepository;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    private final ProductOptionCategoryRepository productOptionCategoryRepository;

    private final OptionRepository optionRepository;

    private final ProductCategoryRepository productCategoryRepository;

    private final OrdersProductRepository ordersProductRepository;

    private final OrdersRepository ordersRepository;

    // 상품 목록 조회-wooyoung-23.08.22
    @Transactional
    public List<ProductDto> findProductList(Integer productCategoryId) {

        if (!productCategoryRepository.existsById(productCategoryId)) {
            throw new CustomException(PRODUCTCATEGORY_NOT_EXISTS);
        }

        List<Product> productList = productRepository.findAllByProductCategoryId(productCategoryId);
        List<ProductDto> productDtoList = new ArrayList<>();

        for (Product product : productList) {
            productDtoList.add(ProductDto.from(product));
        }

        return productDtoList;
    }

    // 상품 상세 정보 조회-wooyoung-23.08.28
    public ProductDetailDto findProductDetails(Integer productId) {

        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new CustomException(PRODUCT_NOT_EXISTS));

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

    // 베스트 상품 목록 조회-minsu-23.09.08
    @Transactional
    public List<BestProductDto> findBestProductList() {

        List<Orders> receivedOrders = ordersRepository.findByReceiptStatus(RECEIVED);

        Map<Product, Integer> productCountMap = new HashMap<>();

        for (Orders orders : receivedOrders) {
            List<OrdersProduct> ordersProducts = ordersProductRepository.findByOrdersId(
                orders.getId());
            for (OrdersProduct ordersProduct : ordersProducts) {
                Product product = ordersProduct.getProduct();
                int count = ordersProduct.getCount();

                if (productCountMap.containsKey(product)) {
                    count += productCountMap.get(product);
                }

                productCountMap.put(product, count);
            }
        }

        List<Product> bestProductList = productCountMap.entrySet().stream()
            .sorted((entry1, entry2) -> Integer.compare(entry2.getValue(), entry1.getValue()))
            .limit(5)
            .map(Map.Entry::getKey)
            .collect(Collectors.toList());

        if (bestProductList.isEmpty()) {
            throw new CustomException(BEST_PRODUCT_NOT_EXISTS);
        }

        return bestProductList.stream()
            .map(product -> BestProductDto.builder()
                .productId(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .soldOutStatus(product.getSoldOutStatus())
                .picture(product.getPicture())
                .build())
            .collect(Collectors.toList());
    }
}