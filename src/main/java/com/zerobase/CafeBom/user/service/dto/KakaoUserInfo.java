package com.zerobase.CafeBom.user.service.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class KakaoUserInfo {

    private Long kakaoId;

    private String nickname;

    private String email;
}