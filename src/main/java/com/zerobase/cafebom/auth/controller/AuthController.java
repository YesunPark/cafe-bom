package com.zerobase.cafebom.auth.controller;

import static com.zerobase.cafebom.exception.ErrorCode.ADMIN_CODE_NOT_MATCH;
import static org.springframework.http.HttpStatus.CREATED;

import com.zerobase.cafebom.auth.dto.SigninDto.Request;
import com.zerobase.cafebom.auth.dto.SigninDto.Response;
import com.zerobase.cafebom.auth.dto.SigninForm;
import com.zerobase.cafebom.auth.dto.SignupAdminForm;
import com.zerobase.cafebom.auth.dto.SignupDto;
import com.zerobase.cafebom.auth.dto.SignupMemberForm;
import com.zerobase.cafebom.auth.service.AuthService;
import com.zerobase.cafebom.exception.CustomException;
import com.zerobase.cafebom.security.TokenProvider;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "auth-controller", description = "회원가입, 로그인 API")
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final TokenProvider tokenProvider;

    @Value("${admin.code}")
    private String ADMIN_CODE;

    // yesun-23.09.05
    @ApiOperation(value = "사용자 회원가입", notes = "이메일, 닉네임, 전화번호, 비밀번호로 회원가입합니다.")
    @PostMapping("/signup")
    public ResponseEntity<Void> memberSignup(
        @RequestBody @Valid SignupMemberForm signupMemberForm
    ) {
        authService.signup(SignupDto.from(signupMemberForm));
        return ResponseEntity.status(CREATED).build();
    }

    // yesun-23.09.05
    @ApiOperation(value = "관리자 회원가입", notes = "관리자 인증코드, 이메일, 비밀번호로 회원가입합니다.")
    @PostMapping("/signup/admin")
    public ResponseEntity<Void> adminSignup(
        @RequestBody @Valid SignupAdminForm signupAdminForm
    ) {
        if (!ADMIN_CODE.equals(signupAdminForm.getAdminCode())) {
            throw new CustomException(ADMIN_CODE_NOT_MATCH);
        }
        authService.signup(signupAdminForm);
        return ResponseEntity.status(CREATED).build();
    }

    // yesun-23.08.22
    @ApiOperation(value = "사용자, 관리자 공통 로그인", notes = "사용자, 관리자 공통으로 이메일, 비밀번호로 로그인합니다.")
    @PostMapping("/signin")
    public ResponseEntity<SigninForm.Response> signin(
        @RequestBody @Valid SigninForm.Request signinForm
    ) {
        Response signinDto = authService.signin(Request.from(signinForm));
        String accessToken = tokenProvider.generateToken(
            signinDto.getMemberId(), signinDto.getEmail(), signinDto.getRole()
        );
        return ResponseEntity.status(HttpStatus.OK)
            .body(
                SigninForm.Response.builder()
                    .token(accessToken).build()
            );
    }
}