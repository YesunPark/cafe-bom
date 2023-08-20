package com.zerobase.CafeBom.user.service;

import com.zerobase.CafeBom.exception.CustomException;
import com.zerobase.CafeBom.type.ErrorCode;
import com.zerobase.CafeBom.type.Role;
import com.zerobase.CafeBom.user.domain.entity.Member;
import com.zerobase.CafeBom.user.repository.MemberRepository;
import com.zerobase.CafeBom.user.service.dto.SignupDto;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService implements UserDetailsService {

    private final MemberRepository memberRepository;

    // 회원가입-yesun-23.08.19
    public void signup(SignupDto signupDto) {
        // 중복 회원가입 예외
        try {
            memberRepository.save(Member.from(signupDto));
        } catch (DataIntegrityViolationException e) {
            throw new CustomException(ErrorCode.NICKNAME_ALREADY_EXISTS);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String kakaoId) throws UsernameNotFoundException {
        Member member = memberRepository.findByKakaoId(kakaoId).orElseThrow(
            () -> new CustomException(ErrorCode.USER_NOT_EXISTS)
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