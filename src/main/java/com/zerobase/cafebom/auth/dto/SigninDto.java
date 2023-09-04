package com.zerobase.cafebom.auth.dto;

import com.zerobase.cafebom.security.Role;
import lombok.Builder;
import lombok.Getter;

public class SigninDto {

    @Getter
    @Builder
    public static class Request {

        private String email;

        private String password;

        public static SigninDto.Request from(SigninForm.Request signinForm) {
            return Request.builder()
                .email(signinForm.getEmail())
                .password(signinForm.getPassword())
                .build();
        }
    }

    @Getter
    @Builder
    public static class Response {

        private Long memberId;

        private String email;

        private Role role;
    }
}
