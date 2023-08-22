package com.zerobase.CafeBom.user.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zerobase.CafeBom.user.controller.dto.KakaoUserInfo;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/kakao")
public class KakaoController {

    @Value("${kakao.client_id}")
    private String kakaoClientId;

    @Value("${kakao.redirect_uri}")
    private String kakaoRedirectUri;

    // 인가 코드 요청-yesun-23.08.17
    @GetMapping("/oauth")
    public String authRedirect() {
        String authUrl = "https://kauth.kakao.com/oauth/authorize";
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(authUrl)
            .queryParam("client_id", kakaoClientId)
            .queryParam("redirect_uri", kakaoRedirectUri)
            .queryParam("response_type", "code");
        return "redirect:" + builder.toUriString();
    }

    // 인가 코드 수령-yesun-23.08.18
    @GetMapping("/callback")
    public String getToken(@RequestParam String code) {
        String accessToken = getKakaoAccessToken(code);
        KakaoUserInfo kakaoUserInfo = getKakaoUserInfo(accessToken);

        return "code : " + code +
            " , access_token: " + accessToken +
            " , nickname: " + kakaoUserInfo.getNickname() +
            " , email: " + kakaoUserInfo.getEmail() +
            " , kakaoId: " + kakaoUserInfo.getKakaoId();
    }

    // 인가 코드로 토큰 수령-yesun-23.08.17
    private String getKakaoAccessToken(String code) {
        WebClient webClient = WebClient.builder()
            .baseUrl("https://kauth.kakao.com")
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
            .build();

        String tokenResponse = webClient.post()
            .uri(uriBuilder -> uriBuilder
                .path("/oauth/token")
                .queryParam("grant_type", "authorization_code")
                .queryParam("client_id", kakaoClientId)
                .queryParam("redirect_uri", kakaoRedirectUri)
                .queryParam("code", code).build())
            .retrieve().bodyToMono(String.class).block();

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode;
        try {
            jsonNode = objectMapper.readTree(tokenResponse);
        } catch (Exception e) {
            e.printStackTrace();
            return "에러가 발생했습니다. 요청을 다시 확인해주십시오.";
        }

        return jsonNode.get("access_token").asText();
    }

    // 토큰으로 프로필정보 수령-yesun-23.08.18
    private KakaoUserInfo getKakaoUserInfo(@RequestBody String accessToken) {
        WebClient webClient = WebClient.builder()
            .baseUrl("https://kapi.kakao.com")
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
            .defaultHeader("Authorization", "Bearer " + accessToken)
            .build();

        JsonNode response = webClient.get()
            .uri(uriBuilder -> uriBuilder
                .path("/v2/user/me")
                .queryParam("grant_type", "authorization_code")
                .queryParam("client_id", kakaoClientId)
                .queryParam("redirect_uri", kakaoRedirectUri)
                .build())
            .retrieve().bodyToMono(JsonNode.class).block();

        return KakaoUserInfo.builder()
            .kakaoId(Objects.requireNonNull(response).path("id").asLong())
            .nickname(response.path("kakao_account")
                .path("profile").path("nickname").asText())
            .email(response.path("kakao_account")
                .path("email").asText())
            .build();
    }
}