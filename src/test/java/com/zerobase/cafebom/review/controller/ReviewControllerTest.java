package com.zerobase.cafebom.review.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zerobase.cafebom.review.controller.form.ReviewAddForm;
import com.zerobase.cafebom.review.service.ReviewService;
import com.zerobase.cafebom.security.TokenProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(ReviewController.class)
class ReviewControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ReviewService reviewService;
    @MockBean
    private TokenProvider tokenProvider;

    // given
    String token = "Bearer testToken";
    ReviewAddForm.Request request = ReviewAddForm.Request.builder()
        .ordersProductId(1L)
        .rating(1)
        .content("test content")
        .picture("test picture")
        .build();

    // yesun-23.09.01
    @Test
    @DisplayName("리뷰 생성 성공 - 토큰, 주문상품정보, 별점, 내용, 사진을 받아 주문 저장")
    void successReviewAdd() throws Exception {
        // when
        mockMvc.perform(post("/auth/review")
                .header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isCreated())
            .andDo(print());
    }

    // yesun-23.09.01
    @Test
    @DisplayName("리뷰 생성 성공 - 토큰, 주문상품정보, 별점만 받아 주문 저장")
    void successReviewAddWithoutContentAndPicture() throws Exception {
        // given
        request = ReviewAddForm.Request.builder()
            .ordersProductId(1L)
            .rating(1)
            .build();

        // when
        mockMvc.perform(post("/auth/review")
                .header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isCreated())
            .andDo(print());
    }

    // yesun-23.09.01
    @Test
    @DisplayName("리뷰 생성 실패 - 헤더에 Authorization 없음")
    void failReviewAddAuthorizationNotPresent() throws Exception {
        // when
        mockMvc.perform(post("/auth/review")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isBadRequest())
            .andDo(print());
    }

    // yesun-23.09.01
    @Test
    @DisplayName("리뷰 생성 실패 - 상품정보 누락")
    void failReviewAddOrdersProductIdIsNotPresent() throws Exception {
        // given
        request = ReviewAddForm.Request.builder()
            .rating(1)
            .content("test content")
            .picture("test picture")
            .build();

        // when
        mockMvc.perform(post("/auth/review")
                .header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.errorMessage")
                .value("주문 상품 ID는 1부터 입력해야 합니다."))
            .andDo(print());
    }

    // yesun-23.09.01
    @Test
    @DisplayName("리뷰 생성 실패 - 별점 누락")
    void failReviewAddRatingIsNotPresent() throws Exception {
        // given
        request = ReviewAddForm.Request.builder()
            .ordersProductId(1L)
            .content("test content")
            .picture("test picture")
            .build();

        // when
        mockMvc.perform(post("/auth/review")
                .header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.errorMessage")
                .value("별점은 1~5점 사이로 입력해야 합니다."))
            .andDo(print());
    }

    // yesun-23.09.01
    @Test
    @DisplayName("리뷰 생성 실패 - 별점 범위 오류")
    void failReviewAddInvalidRating() throws Exception {
        // given
        request = ReviewAddForm.Request.builder()
            .ordersProductId(1L)
            .rating(10)
            .content("test content")
            .picture("test picture")
            .build();

        // when
        mockMvc.perform(post("/auth/review")
                .header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.errorMessage")
                .value("별점은 1~5점 사이로 입력해야 합니다."))
            .andDo(print());
    }
}