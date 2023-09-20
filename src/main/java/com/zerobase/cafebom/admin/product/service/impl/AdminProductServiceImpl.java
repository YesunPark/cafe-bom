package com.zerobase.cafebom.admin.product.service.impl;


import com.zerobase.cafebom.admin.product.dto.AdminProductDto;
import com.zerobase.cafebom.admin.product.dto.AdminProductForm;
import com.zerobase.cafebom.admin.product.service.AdminProductService;
import com.zerobase.cafebom.common.S3UploaderService;
import com.zerobase.cafebom.common.exception.CustomException;
import com.zerobase.cafebom.front.product.domain.Product;
import com.zerobase.cafebom.front.product.domain.ProductRepository;
import com.zerobase.cafebom.front.product.domain.ProductCategory;
import com.zerobase.cafebom.front.product.domain.ProductCategoryRepository;
import com.zerobase.cafebom.common.type.SoldOutStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static com.zerobase.cafebom.common.exception.ErrorCode.PRODUCTCATEGORY_NOT_EXISTS;
import static com.zerobase.cafebom.common.exception.ErrorCode.PRODUCT_NOT_EXISTS;

@Service
@RequiredArgsConstructor
public class AdminProductServiceImpl implements AdminProductService {

    private final ProductRepository productRepository;
    private final ProductCategoryRepository productCategoryRepository;
    private final S3UploaderService s3UploaderService;

    // 관리자 상품 등록-jiyeon-23.08.25
    @Override
    public void addProduct(MultipartFile image, AdminProductDto adminProductDto)
            throws IOException {
        String pictureUrl = s3UploaderService.uploadFileToS3(image, "product");

        Integer productCategoryId = adminProductDto.getProductCategoryId();
        ProductCategory productCategory = productCategoryRepository.findById(productCategoryId)
                .orElseThrow(() -> new CustomException(PRODUCTCATEGORY_NOT_EXISTS));

        productRepository.save(Product.builder()
                .name(adminProductDto.getName())
                .description(adminProductDto.getDescription())
                .productCategory(productCategory)
                .price(adminProductDto.getPrice())
                .soldOutStatus(adminProductDto.getSoldOutStatus())
                .picture(pictureUrl)
                .build());
    }

    // 관리자 상품 수정-jiyeon-23.08.25
    @Override
    @Transactional
    public void modifyProduct(MultipartFile image, Integer id, AdminProductDto adminProductDto)
            throws IOException {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new CustomException(PRODUCT_NOT_EXISTS));

        ProductCategory productCategory = productCategoryRepository.findById(
                        adminProductDto.getProductCategoryId())
                .orElseThrow(() -> new CustomException(PRODUCTCATEGORY_NOT_EXISTS));

        product.modifyProductForm(adminProductDto, productCategory);

        if (image != null && !image.isEmpty()) {
            String oldPicture = product.getPicture();
            if (oldPicture != null) {
                s3UploaderService.deleteFile(oldPicture);
            }
            String newImageUrl = s3UploaderService.uploadFileToS3(image, "product");

            product.modifyNewImageUrl(newImageUrl);
        }
    }

    // 관리자 상품 삭제-jiyeon-23.08.25
    @Override
    public void removeProduct(Integer id) throws IOException {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new CustomException(PRODUCT_NOT_EXISTS));

        s3UploaderService.deleteFile(product.getPicture());

        productRepository.deleteById(product.getId());
    }

    // 관리자 상품 전체 조회-jiyeon-23.08.31
    @Override
    public List<AdminProductForm.Response> findProductList() {
        List<Product> productList = productRepository.findAll();

        List<AdminProductForm.Response> productFormList = productList.stream()
                .map(AdminProductForm.Response::from)
                .collect(Collectors.toList());

        return productFormList;
    }

    // 관리자 상품Id별 조회-jiyeon-23.08.31
    @Override
    public AdminProductForm.Response findProductById(Integer id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new CustomException(PRODUCT_NOT_EXISTS));

        AdminProductForm.Response productForm = AdminProductForm.Response.from(product);

        return productForm;
    }

    // 관리자 상품 품절여부 수정-jiyeon-23.08.29
    @Override
    @Transactional
    public void modifyProductSoldOut(Integer productId, SoldOutStatus soldOutStatus) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new CustomException(PRODUCT_NOT_EXISTS));

        product.modifySoldOutStatus(soldOutStatus);

        productRepository.save(product);
    }

}
