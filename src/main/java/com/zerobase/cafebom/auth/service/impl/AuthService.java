package com.zerobase.cafebom.auth.service.impl;

import static com.zerobase.cafebom.common.config.security.Role.ROLE_ADMIN;
import static com.zerobase.cafebom.common.config.security.Role.ROLE_USER;
import static com.zerobase.cafebom.common.exception.ErrorCode.EMAIL_ALREADY_EXISTS;
import static com.zerobase.cafebom.common.exception.ErrorCode.MEMBER_NOT_EXISTS;
import static com.zerobase.cafebom.common.exception.ErrorCode.NICKNAME_ALREADY_EXISTS;
import static com.zerobase.cafebom.common.exception.ErrorCode.PASSWORD_NOT_MATCH;

import com.zerobase.cafebom.auth.dto.SigninDto;
import com.zerobase.cafebom.auth.dto.SigninForm;
import com.zerobase.cafebom.auth.dto.SignupAdminForm;
import com.zerobase.cafebom.auth.dto.SignupDto;
import com.zerobase.cafebom.auth.dto.SignupMemberForm;
import com.zerobase.cafebom.common.exception.CustomException;
import com.zerobase.cafebom.front.member.domain.Member;
import com.zerobase.cafebom.front.member.domain.MemberRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;

    // 사용자 회원가입-yesun-23.09.24
    public void signup(SignupMemberForm.Request request) {
        SignupDto signupDto = SignupDto.from(request);
        if (memberRepository.findByNickname(request.getNickname()).isPresent()) {
            throw new CustomException(NICKNAME_ALREADY_EXISTS);
        }
        if (memberRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new CustomException(EMAIL_ALREADY_EXISTS);
        }
        memberRepository.save(
            Member.from(signupDto, passwordEncoder.encode(signupDto.getPassword()), ROLE_USER));
    }

    // 관리자 회원가입-yesun-23.09.05
    public void signup(SignupAdminForm.Request signupAdminForm) {
        SignupDto signupDto = SignupDto.from(signupAdminForm);

        memberRepository.findByEmail(signupDto.getEmail()).ifPresent(member -> {
            throw new CustomException(EMAIL_ALREADY_EXISTS);
        });

        memberRepository.save(
            Member.from(signupDto, passwordEncoder.encode(signupDto.getPassword()), ROLE_ADMIN));
    }

    // 공통 로그인-yesun-23.09.24
    public SigninDto.Response signin(SigninForm.Request request) {
        Member member = memberRepository.findByEmail(request.getEmail())
            .orElseThrow(() -> new CustomException(MEMBER_NOT_EXISTS));

        if (!passwordEncoder.matches(request.getPassword(), member.getPassword())) {
            throw new CustomException(PASSWORD_NOT_MATCH);
        }

        return SigninDto.Response.from(member);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(email).orElseThrow(
            () -> new CustomException(MEMBER_NOT_EXISTS)
        );

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();

        if (member.getRole().toString().equals(ROLE_ADMIN.toString())) {
            grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        } else if (member.getRole().toString().equals(ROLE_USER.toString())) {
            grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        }

        return User.builder()
            .username(member.getEmail())
            .password(member.getPassword())
            .authorities(grantedAuthorities)
            .build();
    }
}