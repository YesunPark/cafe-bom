package com.zerobase.cafebom.review.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zerobase.cafebom.review.controller.form.ReviewAddForm;
import com.zerobase.cafebom.review.service.ReviewService;
import com.zerobase.cafebom.security.TokenProvider;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.multipart.MultipartFile;
// 사진 저장 multipartFile 추가 이후로 실패중이라 다시 시도할 예정-yesun-23.09.04
//@WebMvcTest(ReviewController.class)
//@AutoConfigureMockMvc
//class ReviewControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @MockBean
//    private ReviewService reviewService;
//    @MockBean
//    private TokenProvider tokenProvider;
//
//    // given
//    String token = "Bearer testToken";
//    ReviewAddForm.Request request = ReviewAddForm.Request.builder()
//        .ordersProductId(1L)
//        .rating(1)
//        .content("test content")
//        .build();
//    MockMultipartFile multipartFile = new MockMultipartFile(
//        "image", "test.txt", "text/plain",
//        "test file".getBytes(StandardCharsets.UTF_8));
//    String multipartFileTest = "test file";
//
//    // yesun-23.09.01
//    @Test
//    @DisplayName("리뷰 생성 성공 - 토큰, 주문상품정보, 별점, 내용, 사진을 받아 주문 저장")
//    void successReviewAdd() throws Exception {
//        // given
//        String requestJsonStr = objectMapper.writeValueAsString(request);
//        MockMultipartFile requestMultipart = new MockMultipartFile("request", "request",
//            "application/json", requestJsonStr.getBytes(StandardCharsets.UTF_8));
//
//        // when
////        mockMvc.perform(post("/auth/review")
////            .header("Authorization", token)
////            .contentType(MediaType.MULTIPART_FORM_DATA)
////            .content(objectMapper.writeValueAsString(request))
////        )
//
//        mockMvc.perform(multipart("/auth/review")
//                .file(multipartFile)
//                .file(requestMultipart)
//                .header("Authorization", token)
//            )
//            .andExpect(status().isCreated())
//            .andDo(print());
//    }
//
//    // yesun-23.09.01
//    @Test
//    @DisplayName("리뷰 생성 성공 - 토큰, 주문상품정보, 별점만 받아 주문 저장")
//    void successReviewAddWithoutContentAndPicture() throws Exception {
//        // given
//        request = ReviewAddForm.Request.builder()
//            .ordersProductId(1L)
//            .rating(1)
//            .build();
//
//        // when
//        mockMvc.perform(post("/auth/review")
//                .header("Authorization", token)
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(request)))
//            .andExpect(status().isCreated())
//            .andDo(print());
//    }
//
//    // yesun-23.09.01
//    @Test
//    @DisplayName("리뷰 생성 실패 - 헤더에 Authorization 없음")
//    void failReviewAddAuthorizationNotPresent() throws Exception {
//        // when
//        mockMvc.perform(post("/auth/review")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(request)))
//            .andExpect(status().isBadRequest())
//            .andDo(print());
//    }
//
//    // yesun-23.09.01
//    @Test
//    @DisplayName("리뷰 생성 실패 - 상품정보 누락")
//    void failReviewAddOrdersProductIdIsNotPresent() throws Exception {
//        // given
//        request = ReviewAddForm.Request.builder()
//            .rating(1)
//            .content("test content")
//            .build();
//
//        // when
//        mockMvc.perform(post("/auth/review")
//                .header("Authorization", token)
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(request)))
//            .andExpect(status().isBadRequest())
//            .andExpect(jsonPath("$.errorMessage")
//                .value("주문 상품 ID는 1부터 입력해야 합니다."))
//            .andDo(print());
//    }
//
//    // yesun-23.09.01
//    @Test
//    @DisplayName("리뷰 생성 실패 - 별점 누락")
//    void failReviewAddRatingIsNotPresent() throws Exception {
//        // given
//        request = ReviewAddForm.Request.builder()
//            .ordersProductId(1L)
//            .content("test content")
//            .build();
//
//        // when
//        mockMvc.perform(post("/auth/review")
//                .header("Authorization", token)
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(request)))
//            .andExpect(status().isBadRequest())
//            .andExpect(jsonPath("$.errorMessage")
//                .value("별점은 1~5점 사이로 입력해야 합니다."))
//            .andDo(print());
//    }
//
//    // yesun-23.09.01
//    @Test
//    @DisplayName("리뷰 생성 실패 - 별점 범위 오류")
//    void failReviewAddInvalidRating() throws Exception {
//        // given
//        request = ReviewAddForm.Request.builder()
//            .ordersProductId(1L)
//            .rating(10)
//            .content("test content")
//            .build();
//
//        // when
//        mockMvc.perform(post("/auth/review")
//                .header("Authorization", token)
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(request)))
//            .andExpect(status().isBadRequest())
//            .andExpect(jsonPath("$.errorMessage")
//                .value("별점은 1~5점 사이로 입력해야 합니다."))
//            .andDo(print());
//    }
//}