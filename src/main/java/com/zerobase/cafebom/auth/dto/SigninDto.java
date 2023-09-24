package com.zerobase.cafebom.auth.dto;

import com.zerobase.cafebom.common.config.security.Role;
import com.zerobase.cafebom.front.member.domain.Member;
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

        public static Response from(Member member) {
            return Response.builder()
                .memberId(member.getId())
                .email(member.getEmail())
                .role(member.getRole())
                .build();
        }
    }
}
