package com.zerobase.cafebom.security;

import static com.zerobase.cafebom.security.Role.ROLE_ADMIN;
import static com.zerobase.cafebom.security.Role.ROLE_USER;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity // 스프링 시큐리티 필터가 스프링 필터체인에 등록
@RequiredArgsConstructor
@Slf4j
public class SecurityConfig {

    private final TokenProvider tokenProvider;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.csrf(AbstractHttpConfigurer::disable)
            .cors(Customizer.withDefaults()) // CORS 설정 허용
            .headers(c -> c.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable).disable())
            .authorizeHttpRequests(auth -> auth
                .antMatchers("/**/signup/**", "/**/signin", "/product/**").permitAll()
                .antMatchers("/h2-console/**").permitAll()
                .antMatchers("/auth/**").hasAuthority(ROLE_USER.name())
                .antMatchers("/admin/**").hasAuthority(ROLE_ADMIN.name())
                .and()
                .addFilterBefore(new JwtAuthenticationFilter(tokenProvider),
                    UsernamePasswordAuthenticationFilter.class)
            );
        return http.build();
    }
}