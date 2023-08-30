package com.zerobase.cafebom.product.controller;

import com.zerobase.cafebom.product.controller.form.ProductForm;
import com.zerobase.cafebom.product.domain.entity.Product;
import com.zerobase.cafebom.product.repository.ProductRepository;
import com.zerobase.cafebom.product.service.ProductService;
import com.zerobase.cafebom.product.service.S3UploaderService;
import com.zerobase.cafebom.security.TokenProvider;
import org.assertj.core.api.Assertions;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static com.zerobase.cafebom.product.domain.entity.SoldOutStatus.IN_STOCK;
import static com.zerobase.cafebom.product.domain.entity.SoldOutStatus.SOLD_OUT;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TokenProvider tokenProvider;

    @MockBean
    private ProductRepository productRepository;

    @MockBean
    private ProductService productService;

    @MockBean
    private S3UploaderService s3UploaderService;

    private InputStream imageStream;

    @BeforeEach
    void setUp() {
        imageStream = getClass().getResourceAsStream("/라떼.jpg");
    }

    @Test
    @WithMockUser(username = "user", roles = "ADMIN")
    @DisplayName("상품 전체조회 성공")
    void successProductAllList() throws Exception {
        // given
        List<Product> productList = new ArrayList<>();

        productList.add(Product.builder()
                .id(1)
                .name("아메리카노")
                .description("아메리카노입니다.")
                .price(2800)
                .soldOutStatus(IN_STOCK)
                .picture("url1")
                .build());

        productList.add(Product.builder()
                .id(2)
                .name("라떼")
                .description("라떼입니다.")
                .price(3800)
                .soldOutStatus(SOLD_OUT)
                .picture("url2")
                .build());

        when(productRepository.findAll()).thenReturn(productList);

        // when
        MvcResult result = mockMvc.perform(get("/admin/product"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        // then
        String responseContent = result.getResponse().getContentAsString();
        JSONArray jsonResponse = new JSONArray(responseContent);

        JSONObject firstJsonObject = jsonResponse.getJSONObject(0);
        Assertions.assertThat(firstJsonObject.getInt("id")).isEqualTo(1);


        JSONObject secondJsonObject = jsonResponse.getJSONObject(1);
        Assertions.assertThat(secondJsonObject.getInt("id")).isEqualTo(2);

    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @DisplayName("관리자 상품 등록 성공 - 관리자일 경우 상품 등록을 할 수 있다.")
    public void successAdminProductAdd() throws Exception {
        // given
        MockMultipartFile imageFile = new MockMultipartFile("image"
                , "아메리카노.jpg"
                , "image/jpeg"
                , getClass().getResourceAsStream("/아메리카노.jpg"));

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("name", "아메리카노");
        params.add("description", "아메리카노 입니다.");
        params.add("price", "2800");
        params.add("soldOutStatus", "IN_STOCK");

        // when
        mockMvc.perform(multipart("/admin/product/add")
                        .file(imageFile)
                        .params(params))

                // then
                .andExpect(MockMvcResultMatchers.status().isCreated());

    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    @DisplayName("관리자가 아닌 경우 상품 등록 실패 - 관리자가 아니면 상품을 등록할 수 없다.")
    public void failAdminProductAdd() throws Exception {
        // given
        MockMultipartFile imageFile = new MockMultipartFile("image"
                , "아메리카노.jpg"
                , "image/jpeg"
                , getClass().getResourceAsStream("/아메리카노.jpg"));

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("name", "아메리카노");
        params.add("description", "아메리카노 입니다.");
        params.add("price", "2800");
        params.add("soldOutStatus", "IN_STOCK");

        // when
        mockMvc.perform(multipart("/admin/product/add")
                        .file(imageFile)
                        .params(params))

                // then
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    @DisplayName("관리자 상품 수정 성공")
    public void successAdminProductModify() throws Exception {
        // given
        MockMultipartFile imageFile = new MockMultipartFile(
                "image",
                "라떼.jpg",
                "image/jpeg",
                getClass().getResourceAsStream("/라떼.jpg")
        );

        given(productService.modifyProduct(any(), anyInt(), any(ProductForm.class))).willReturn(true);

        // when
        mockMvc.perform(
                        MockMvcRequestBuilders.multipart("/admin/product/{id}", 1)
                                .file(imageFile)
                                .param("name", "라떼")
                                .param("description", "라떼입니다.")
                                .param("price", "3800")
                                .param("picture", "newURL")
                                .param("soldOutStatus", "SOLD_OUT")
                                .with(request -> {
                                    request.setMethod("PUT");
                                    return request;
                                }))

                // then
                .andExpect(status().isOk());
        verify(productService).modifyProduct(any(), eq(1), any(ProductForm.class));

    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    @DisplayName("관리자가 아닌 회원일때 상품 등록 실패")
    public void failAdminProductModify() throws Exception {
        // given
        MockMultipartFile imageFile = new MockMultipartFile(
                "image",
                "라떼.jpg",
                "image/jpeg",
                getClass().getResourceAsStream("/라떼.jpg")
        );

        given(productService.modifyProduct(any(), anyInt(), any(ProductForm.class))).willReturn(true);

        // when
        mockMvc.perform(
                        MockMvcRequestBuilders.multipart("/admin/product/{id}", 1)
                                .file(imageFile)
                                .param("name", "라떼")
                                .param("description", "라떼입니다.")
                                .param("price", "3800")
                                .param("picture", "newURL")
                                .param("soldOutStatus", "SOLD_OUT")
                                .with(request -> {
                                    request.setMethod("PUT");
                                    return request;
                                }))

                // then
                .andExpect(status().isForbidden());

        verify(productService, never()).modifyProduct(any(), eq(1), any(ProductForm.class));

    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    @DisplayName("관리자일때 상품 삭제 성공")
    public void successAdminProductRemove() throws Exception {
        // given
        when(productService.removeProduct(any(Integer.class))).thenReturn(true);

        // when & then
        mockMvc.perform(delete("/admin/product/{id}", 1)
                        .with(csrf()))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "admin", roles = "USER")
    @DisplayName("관리자일때 상품 삭제 실패")
    public void failAdminProductRemove() throws Exception {
        // given
        when(productService.removeProduct(any(Integer.class))).thenReturn(true);

        // when & then
        mockMvc.perform(delete("/admin/product/{id}", 1)
                        .with(csrf()))
                .andExpect(status().isForbidden());
    }



}
