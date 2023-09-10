package com.zerobase.cafebom.security;

import static com.zerobase.cafebom.security.JwtAuthenticationFilter.TOKEN_PREFIX;

import com.zerobase.cafebom.auth.service.AuthService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Slf4j
@Component
@RequiredArgsConstructor
public class TokenProvider {

    private static final String KEY_ROLE = "role";
    private static final long TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 24; // 1일 (24시간)

    private final AuthService authService;

    @Value("${spring.jwt.secret}")
    private String secretKey;

    // 토큰 생성(발급)-yesun-23.08.21
    public String generateToken(Long userId, String email, Role role) {
        Claims claims = Jwts.claims() // 사용자의 정보를 저장하기 위한 claim
            .setSubject(email)
            .setId(userId + "");
        claims.put(KEY_ROLE, role);

        Date now = new Date();
        Date expiredDate = new Date(now.getTime() + TOKEN_EXPIRE_TIME);

        return Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(now) // 토큰 생성 시간
            .setExpiration(expiredDate) // 토큰 만료 시간
            .signWith(SignatureAlgorithm.HS512, secretKey) // 사용할 암호화 알고리즘, 시크릿 키
            .compact();
    }

    // jwt 에서 인증정보 추출-yesun-23.09.09
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = authService.loadUserByUsername(getEmail(token));
        List<GrantedAuthority> authorities = new ArrayList<>(userDetails.getAuthorities());
        return new UsernamePasswordAuthenticationToken(userDetails, "", authorities);
    }

    // 토큰에서 사용자 이메일 추출-yesun-23.08.25
    public String getEmail(String token) {
        return parseClaims(token).getSubject();
    }

    // 토큰에서 사용자 id 추출-yesun-23.08.25
    public Long getId(String token) {
        return Long.parseLong(parseClaims(removeBearerFromToken(token)).getId());
    }

    // 토큰 유효성검사-yesun-23.08.21
    public boolean validateToken(String token) {
        if (!StringUtils.hasText(token)) {
            return false;
        }

        Claims claims = parseClaims(token);
        return !claims.getExpiration().before(new Date());
    }

    // 토큰에서 클레임 정보 추출-yesun-23.09.10
    private Claims parseClaims(String token) {
        try {
            return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException e) {
            log.warn("만료된 JWT 토큰입니다.");
            return e.getClaims();
        }
    }

    // 토큰 인증 타입 제거-yesun-23.08.25
    private String removeBearerFromToken(String token) {
        return token.substring(TOKEN_PREFIX.length());
    }
}