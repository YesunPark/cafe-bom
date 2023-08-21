package com.zerobase.cafebom.user.service.dto;

import com.zerobase.cafebom.user.controller.dto.SignupForm;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SignupDto {

    private String password;

    private String nickname;

    private String phone;

    private String email;

    public static SignupDto from(SignupForm signupForm) {
        return SignupDto.builder()
            .password(signupForm.getPassword())
            .nickname(signupForm.getNickname())
            .phone(signupForm.getPhone())
            .email(signupForm.getEmail())
            .build();
    }
}