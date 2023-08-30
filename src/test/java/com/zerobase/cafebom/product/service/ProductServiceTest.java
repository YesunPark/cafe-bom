package com.zerobase.cafebom.product.service;

import com.zerobase.cafebom.product.controller.form.ProductForm;
import com.zerobase.cafebom.product.domain.entity.Product;
import com.zerobase.cafebom.product.repository.ProductRepository;
import com.zerobase.cafebom.product.service.dto.ProductDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

import static com.zerobase.cafebom.product.domain.entity.SoldOutStatus.IN_STOCK;
import static com.zerobase.cafebom.product.domain.entity.SoldOutStatus.SOLD_OUT;
import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @InjectMocks
    private ProductServiceImpl productService;

    @Mock
    private S3UploaderService s3UploaderService;

    @Mock
    private ProductRepository productRepository;

    // jiyeon-23.08.25
    @Test
    @DisplayName("상품 저장 확인 테스트")
    public void successAddAdminProduct() throws IOException {
        // given
        MockMultipartFile mockImage = new MockMultipartFile("test.jpg", new byte[0]);
        when(s3UploaderService.uploadFileToS3(any(MultipartFile.class), eq("dirName"))).thenReturn("mockImageUrl");

        ProductForm productDto = ProductForm.builder()
                .name("아메리카노")
                .description("아메리카노입니다.")
                .price(2800)
                .soldOutStatus(IN_STOCK)
                .picture("url")
                .build();

        Product expectedProduct = Product.builder()
                .name("아메리카노")
                .description("아메리카노입니다.")
                .price(2800)
                .soldOutStatus(IN_STOCK)
                .picture("mockImageUrl")
                .build();

        when(productRepository.save(any(Product.class))).thenReturn(expectedProduct);

        // when
        ProductDto savedProduct = productService.addProduct(mockImage, productDto);

        // then
        verify(s3UploaderService, times(1)).uploadFileToS3(any(MultipartFile.class), eq("dirName"));
        verify(productRepository, times(1)).save(any(Product.class));

        assertNotNull(savedProduct);
        assertEquals(expectedProduct.getName(), savedProduct.getName());
        assertEquals(expectedProduct.getDescription(), savedProduct.getDescription());

    }

    // jiyeon-23.08.25
    @Test
    @DisplayName("상품 업데이트 확인 성공 테스트")
    public void successModifyProduct() throws IOException {
        // given
        MockMultipartFile mockImage = new MockMultipartFile("test.jpg", new byte[0]);

        ProductForm updatedProductForm = ProductForm.builder()
                .name("아메리카노")
                .description("아메리카노입니다.")
                .price(2800)
                .soldOutStatus(IN_STOCK)
                .picture("updatedUrl")
                .build();

        Product existingProduct = Product.builder()
                .name("라떼")
                .description("라떼입니다")
                .price(3000)
                .soldOutStatus(SOLD_OUT)
                .picture("oldUrl")
                .build();

        when(productRepository.findById(1)).thenReturn(Optional.of(existingProduct));

        // when
        boolean isUpdated = productService.modifyProduct(mockImage, 1, updatedProductForm);

        // then
        assertTrue(isUpdated);
        verify(productRepository, times(1)).save(any(Product.class));

    }

    // jiyeon-23.08.25
    @Test
    @DisplayName("상품 업데이트 확인 실패 테스트")
    public void failModifyProductFail() throws IOException {
        // given
        MockMultipartFile mockImage = new MockMultipartFile("test.jpg", new byte[0]);

        ProductForm updatedProductForm = ProductForm.builder()
                .name("아메리카노")
                .description("아메리카노입니다.")
                .price(2800)
                .soldOutStatus(IN_STOCK)
                .picture("updatedUrl")
                .build();

        when(productRepository.findById(1)).thenReturn(Optional.empty());

        // when
        boolean isUpdated = productService.modifyProduct(mockImage, 1, updatedProductForm);

        // then
        assertFalse(isUpdated);
        verify(s3UploaderService, never()).deleteFile(anyString());
        verify(s3UploaderService, never()).uploadFileToS3(any(MultipartFile.class), anyString());
        verify(productRepository, never()).save(any(Product.class));

    }

    // jiyeon-23.08.29
    @Test
    @DisplayName("상품 삭제 성공 테스트")
    void testRemoveProductSuccess() {
        // given
        Integer productId = 1;
        Product product = Product.builder()
                .name("아메리카노")
                .description("아메리카노입니다.")
                .price(2800)
                .soldOutStatus(SOLD_OUT)
                .picture("url")
                .build();

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        // when
        boolean result = productService.removeProduct(productId);

        // then
        assertTrue(result);
        verify(productRepository, times(1)).findById(productId);
        verify(productRepository, times(1)).deleteById(productId);

    }

    // jiyeon-23.08.29
    @Test
    @DisplayName("상품 삭제 실패 테스트")
    void failRemoveAdminProduct() {
        // given
        Integer productId = 1;
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        // when
        boolean result = productService.removeProduct(productId);

        // then
        assertFalse(result);
        verify(productRepository, times(1)).findById(productId);
        verify(productRepository, times(0)).deleteById(productId);

    }

}