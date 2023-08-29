package com.zerobase.cafebom.product.service;


import com.zerobase.cafebom.member.repository.MemberRepository;
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

import javax.persistence.EntityManager;
import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductCategoryRepository productCategoryRepository;
    private final S3UploaderService s3UploaderService;
    private final MemberRepository memberRepository;
    private final EntityManager entityManager;

    // 관리자 상품 등록-jiyeon-23.08.25
    @Transactional
    @Override
    public ProductDto addProduct(MultipartFile image, ProductForm productForm) throws IOException {
        String pictureUrl = s3UploaderService.uploadFileToS3(image, "dirName");

        Integer productCategoryId = productForm.getProductCategoryId();
        ProductCategory productCategory = productCategoryRepository.findById(productCategoryId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid product category id: " + productCategoryId));

        Product product = Product.builder()
                .name(productForm.getName())
                .description(productForm.getDescription())
                .productCategory(productCategory)
                .price(productForm.getPrice())
                .soldOutStatus(productForm.getSoldOutStatus())
                .picture(pictureUrl)
                .build();
        Product productAdd = productRepository.save(product);
        return ProductDto.from(productAdd);
    }

    // 관리자 상품 수정-jiyeon-23.08.25
    @Override
    @Transactional
    public boolean modifyProduct(MultipartFile image, Integer id, ProductForm productForm) throws IOException {
        Optional<Product> updateProduct = productRepository.findById(id);

        if (updateProduct.isPresent()) {
            Product productToUpdate = updateProduct.get();

            productToUpdate.modifyProductForm(productForm);

            if (image != null && !image.isEmpty()) {
                String oldPicture = productToUpdate.getPicture();
                if (oldPicture != null) {
                    s3UploaderService.deleteFile(oldPicture);
                }

                String newImageUrl = s3UploaderService.uploadFileToS3(image, "dirName");

                productToUpdate = productToUpdate.toBuilder()
                        .picture(newImageUrl)
                        .build();
            }

            System.out.println("productToUpdate = " + productToUpdate.toString());
            return true;
        }

        return false;
    }

    // 관리자 상품 삭제-jiyeon-23.08.25
    @Override
    public boolean removeProduct(Integer id) {
        Optional<Product> productDeleteId = productRepository.findById(id);
        if (productDeleteId.isPresent()) {
            productRepository.deleteById(id);
            return true;
        }
        return false;
    }

}
