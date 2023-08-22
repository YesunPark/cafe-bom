package com.zerobase.cafebom.user.service.dto;

import com.zerobase.cafebom.user.security.Role;
import com.zerobase.cafebom.user.controller.dto.SigninForm;
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
