package com.zerobase.cafebom.front.product.service.impl;

import static com.zerobase.cafebom.common.exception.ErrorCode.BEST_PRODUCT_NOT_EXISTS;
import static com.zerobase.cafebom.common.exception.ErrorCode.PRODUCTCATEGORY_NOT_EXISTS;
import static com.zerobase.cafebom.common.exception.ErrorCode.PRODUCT_NOT_EXISTS;
import static com.zerobase.cafebom.common.type.OrderReceiptStatus.RECEIVED;

import com.zerobase.cafebom.common.exception.CustomException;
import com.zerobase.cafebom.front.order.domain.Order;
import com.zerobase.cafebom.front.product.domain.Option;
import com.zerobase.cafebom.front.product.domain.OptionRepository;
import com.zerobase.cafebom.front.order.domain.OrderRepository;
import com.zerobase.cafebom.front.order.domain.OrderProduct;
import com.zerobase.cafebom.front.order.domain.OrderProductRepository;
import com.zerobase.cafebom.front.product.domain.Product;
import com.zerobase.cafebom.front.product.domain.ProductRepository;
import com.zerobase.cafebom.front.product.dto.BestProductDto;
import com.zerobase.cafebom.front.product.dto.ProductDetailDto;
import com.zerobase.cafebom.front.product.dto.ProductDto;
import com.zerobase.cafebom.front.product.domain.ProductCategoryRepository;
import com.zerobase.cafebom.front.product.domain.ProductOptionCategory;
import com.zerobase.cafebom.front.product.domain.ProductOptionCategoryRepository;
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

    private final OrderProductRepository orderProductRepository;

    private final OrderRepository orderRepository;

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

        List<Order> receivedOrders = orderRepository.findByReceiptStatus(RECEIVED);

        Map<Product, Integer> productQuantityMap = new HashMap<>();

        for (Order order : receivedOrders) {
            List<OrderProduct> orderProducts = orderProductRepository.findByOrderId(
                order.getId());
            for (OrderProduct orderProduct : orderProducts) {
                Product product = orderProduct.getProduct();
                int quantity = orderProduct.getQuantity();

                if (productQuantityMap.containsKey(product)) {
                    quantity += productQuantityMap.get(product);
                }

                productQuantityMap.put(product, quantity);
            }
        }

        List<Product> bestProductList = productQuantityMap.entrySet().stream()
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