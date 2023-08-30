package com.zerobase.cafebom.product.service;

import com.zerobase.cafebom.product.controller.form.SoldOutStatusForm;
import com.zerobase.cafebom.product.domain.entity.Product;
import com.zerobase.cafebom.product.domain.type.SoldOutStatus;
import com.zerobase.cafebom.product.repository.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

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

    // jiyeon-23.08.29
    @Test
    @DisplayName("관리자 상품 품절여부 수정 성공")
    public void successAdminProductSoldOut() {
        // given
        Integer productId = 1;
        SoldOutStatusForm form = SoldOutStatusForm.builder()
                .soldOutStatus("SOLD_OUT")
                .build();

        Product mockProduct = Product.builder()
                .name("아메리카노")
                .description("아메리카노입니다.")
                .price(2800)
                .soldOutStatus(SoldOutStatus.SOLD_OUT)
                .picture("url")
                .build();;

        when(productRepository.findById(eq(productId))).thenReturn(Optional.of(mockProduct));
        when(productRepository.save(any(Product.class))).thenReturn(mockProduct);

        // when
        boolean result = productService.modifyProductSoldOut(productId, form);

        // then
        assertTrue(result);
        verify(productRepository, times(1)).findById(eq(productId));
        verify(productRepository, times(1)).save(any(Product.class));
        assertEquals(SoldOutStatus.SOLD_OUT, mockProduct.getSoldOutStatus());
    }

    // jiyeon-23.08.29
    @Test
    @DisplayName("관리자 상품 품절여부 수정 실패")
    public void failAdminProductSoldOut() {
        // given
        Integer productId = 1;
        SoldOutStatusForm form = SoldOutStatusForm.builder()
                .soldOutStatus(SoldOutStatus.SOLD_OUT)
                .build();

        when(productRepository.findById(eq(productId))).thenReturn(Optional.empty());

        // when
        boolean result = productService.modifyProductSoldOut(productId, form);

        // then
        assertFalse(result);
        verify(productRepository, times(1)).findById(eq(productId));
        verify(productRepository, never()).save(any(Product.class));
    }
}