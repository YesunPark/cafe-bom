package com.zerobase.CafeBom.user.controller.dto;

import com.zerobase.CafeBom.type.Role;
import com.zerobase.CafeBom.user.domain.entity.Member;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class KakaoUserInfo {

    private Long kakaoId;

    private String nickname;

    private String email;

    public static Member toEntity(KakaoUserInfo kakaoUserInfo) {
        return Member.builder()
            .kakaoId(kakaoUserInfo.getKakaoId().toString())
            .password(null)
            .nickname(kakaoUserInfo.nickname)
            .phone(null)
            .email(kakaoUserInfo.email)
            .role(Role.ROLE_USER)
            .build();
    }
}