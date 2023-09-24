package com.zerobase.cafebom.auth.controller;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zerobase.cafebom.auth.dto.SignupMemberForm;
import com.zerobase.cafebom.auth.service.impl.AuthService;
import com.zerobase.cafebom.common.config.security.TokenProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AuthService authService;
    @MockBean
    private TokenProvider tokenProvider;

    // yesun-23.09.05
    @Test
    @DisplayName("회원가입 성공 - 이메일, 휴대폰번호, 닉네임, 비밀번호 입력")
    void successSignup() throws Exception {
        // given
        SignupMemberForm form = SignupMemberForm.builder()
            .email("test@test.com")
            .phone("01011112222")
            .nickname("테스트닉넴")
            .password("test12345@")
            .build();

        // when
        mockMvc.perform(post("/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(form))
                .with(csrf()))
            .andExpect(status().isCreated())
            .andDo(print());
    }

    // yesun-23.08.22
    @Test
    @DisplayName("회원가입 실패 - 요청형식 오류(이메일 누락)")
    void failSignupEmailNotBlank() throws Exception {
        // given
        SignupMemberForm form = SignupMemberForm.builder()
            .phone("01011112222")
            .nickname("테스트닉넴")
            .password("test12345@")
            .build();

        // when
        mockMvc.perform(post("/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(form))
                .with(csrf()))
            .andExpect(status().isBadRequest())
            .andDo(print());
    }

    // yesun-23.08.22
    @Test
    @DisplayName("회원가입 실패 - 이메일 형식 오류")
    void failSignupEmailInvalid() throws Exception {
        // given
        SignupMemberForm form = SignupMemberForm.builder()
            .email("test@")
            .phone("01011112222")
            .nickname("테스트닉넴")
            .password("test12345@")
            .build();

        // when
        mockMvc.perform(post("/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(form))
                .with(csrf()))
            .andExpect(status().isBadRequest())
//            .andExpect(jsonPath("$.errorM").value(1))
//            .andExpect(jsonPath("$.accountNumber").value("89300000000")) // exception 머지되면 추가
            .andDo(print());
    }

    // yesun-23.08.22
    @Test
    @DisplayName("회원가입 실패 - 비밀번호 형식 오류")
    void failSignupPasswordInvalid() throws Exception {
        // given
        SignupMemberForm form = SignupMemberForm.builder()
            .email("test@test.com")
            .phone("01011112222")
            .nickname("테스트닉넴")
            .password("test12345@!!!!!!!!!")
            .build();

        // when
        mockMvc.perform(post("/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(form))
                .with(csrf()))
            .andExpect(status().isBadRequest())
//            .andExpect(jsonPath("$.errorM").value(1))
//            .andExpect(jsonPath("$.accountNumber").value("89300000000")) // exception 머지되면 추가
            .andDo(print());
    }
}