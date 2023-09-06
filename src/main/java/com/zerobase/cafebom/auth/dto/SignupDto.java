package com.zerobase.cafebom.auth.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SignupDto {

    private String password;

    private String nickname;

    private String phone;

    private String email;

    public static SignupDto from(SignupMemberForm signupMemberForm) {
        return SignupDto.builder()
            .password(signupMemberForm.getPassword())
            .nickname(signupMemberForm.getNickname())
            .phone(signupMemberForm.getPhone())
            .email(signupMemberForm.getEmail())
            .build();
    }

    public static SignupDto from(SignupAdminForm signupAdminForm) {
        return SignupDto.builder()
            .password(signupAdminForm.getPassword())
            .nickname(signupAdminForm.getAdminName())
            .phone(signupAdminForm.getPhone())
            .email(signupAdminForm.getEmail())
            .build();
    }
}