package com.zerobase.cafebom.product.service;


import com.zerobase.cafebom.exception.CustomException;
import com.zerobase.cafebom.exception.ErrorCode;
import com.zerobase.cafebom.product.controller.form.ProductForm;
import com.zerobase.cafebom.product.domain.entity.Product;
import com.zerobase.cafebom.product.repository.ProductRepository;
import com.zerobase.cafebom.product.service.dto.ProductDto;
import com.zerobase.cafebom.productcategory.domain.entity.ProductCategory;
import com.zerobase.cafebom.productcategory.repository.ProductCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static com.zerobase.cafebom.exception.ErrorCode.NOT_FOUND_PRODUCT;
import static com.zerobase.cafebom.exception.ErrorCode.NOT_FOUND_PRODUCT_CATEGORY;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductCategoryRepository productCategoryRepository;
    private final S3UploaderService s3UploaderService;

    // 관리자 상품 등록-jiyeon-23.08.25
    @Override
    public void addProduct(MultipartFile image, ProductDto productDto) throws IOException {
        String pictureUrl = s3UploaderService.uploadFileToS3(image, "dirName");

        Integer productCategoryId = productDto.getProductCategoryId();
        ProductCategory productCategory = productCategoryRepository.findById(productCategoryId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_PRODUCT_CATEGORY));

        productRepository.save(Product.builder()
                .name(productDto.getName())
                .description(productDto.getDescription())
                .productCategory(productCategory)
                .price(productDto.getPrice())
                .soldOutStatus(productDto.getSoldOutStatus())
                .picture(pictureUrl)
                .build());
    }

    // 관리자 상품 수정-jiyeon-23.08.25
    @Override
    @Transactional
    public void modifyProduct(MultipartFile image, Integer id, ProductDto productDto) throws IOException {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new CustomException(NOT_FOUND_PRODUCT));

        ProductCategory productCategory = productCategoryRepository.findById(productDto.getProductCategoryId())
                .orElseThrow(() -> new CustomException(NOT_FOUND_PRODUCT_CATEGORY));

        product.modifyProductForm(productDto,productCategory);

        if (image != null && !image.isEmpty()) {
            String oldPicture = product.getPicture();
            if (oldPicture != null) {
                s3UploaderService.deleteFile(oldPicture);
            }
            String newImageUrl = s3UploaderService.uploadFileToS3(image, "dirName");
            product.toBuilder()
                    .picture(newImageUrl)
                    .build();
        }
    }

    // 관리자 상품 삭제-jiyeon-23.08.25
    @Override
    public void removeProduct(Integer id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new CustomException(NOT_FOUND_PRODUCT));
        productRepository.deleteById(product.getId());
    }

    // 관리자 상품 전체 조회-jiyeon-23.08.31
    @Override
    public List<ProductForm.Response> findProductList() {
        List<Product> productList = productRepository.findAll();
        List<ProductForm.Response> productFormList = productList.stream()
                .map(ProductForm.Response::from)
                .collect(Collectors.toList());
        return productFormList;
    }

    // 관리자 상품Id별 조회-jiyeon-23.08.31
    @Override
    public ProductForm.Response findProductById(Integer id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new CustomException(NOT_FOUND_PRODUCT));
        ProductForm.Response productForm = ProductForm.Response.from(product);
        return productForm;
    }

}
