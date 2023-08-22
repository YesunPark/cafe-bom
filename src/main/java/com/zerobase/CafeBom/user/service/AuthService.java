package com.zerobase.cafebom.user.service;

import static com.zerobase.cafebom.exception.ErrorCode.EMAIL_ALREADY_EXISTS;
import static com.zerobase.cafebom.exception.ErrorCode.MEMBER_NOT_EXISTS;
import static com.zerobase.cafebom.exception.ErrorCode.NICKNAME_ALREADY_EXISTS;
import static com.zerobase.cafebom.exception.ErrorCode.PASSWORD_NOT_MATCH;

import com.zerobase.cafebom.exception.CustomException;
import com.zerobase.cafebom.type.Role;
import com.zerobase.cafebom.user.domain.entity.Member;
import com.zerobase.cafebom.user.repository.MemberRepository;
import com.zerobase.cafebom.user.service.dto.SigninDto;
import com.zerobase.cafebom.user.service.dto.SignupDto;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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


    // 사용자 회원가입-yesun-23.08.21
    public void signup(SignupDto signupDto) {
        if (memberRepository.findByNickname(signupDto.getNickname()).isPresent()) {
            throw new CustomException(NICKNAME_ALREADY_EXISTS);
        }
        if (memberRepository.findByEmail(signupDto.getEmail()).isPresent()) {
            throw new CustomException(EMAIL_ALREADY_EXISTS);
        }
        memberRepository.save(
            Member.from(signupDto, passwordEncoder.encode(signupDto.getPassword())));
    }

    // 공통 로그인-yesun-23.08.21
    public SigninDto.Response signin(SigninDto.Request signinDto) {
        Member member = memberRepository.findByEmail(signinDto.getEmail())
            .orElseThrow(() -> new CustomException(MEMBER_NOT_EXISTS));

        if (!passwordEncoder.matches(signinDto.getPassword(), member.getPassword())) {
            throw new CustomException(PASSWORD_NOT_MATCH);
        }
        return SigninDto.Response.builder()
            .memberId(member.getId())
            .email(member.getEmail())
            .role(member.getRole())
            .build();
    }

    @Override
    public UserDetails loadUserByUsername(String nickname) throws UsernameNotFoundException {
        Member member = memberRepository.findByNickname(nickname).orElseThrow(
            () -> new CustomException(MEMBER_NOT_EXISTS)
        );

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();

        if (member.getRole().equals(Role.ROLE_ADMIN)) {
            grantedAuthorities.add(new SimpleGrantedAuthority(Role.ROLE_ADMIN.toString()));
        } else {
            grantedAuthorities.add(new SimpleGrantedAuthority(Role.ROLE_USER.toString()));
        }

        return new Member(member.getId().toString(), member.getPassword(),
            grantedAuthorities);
    }
}