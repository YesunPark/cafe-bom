package com.zerobase.cafebom.user.controller;

import com.zerobase.cafebom.user.security.TokenProvider;
import com.zerobase.CafeBom.user.controller.dto.SigninForm;
import com.zerobase.CafeBom.user.controller.dto.SignupForm;
import com.zerobase.CafeBom.user.service.AuthService;
import com.zerobase.CafeBom.user.service.dto.SigninDto.Request;
import com.zerobase.CafeBom.user.service.dto.SigninDto.Response;
import com.zerobase.CafeBom.user.service.dto.SignupDto;
import io.swagger.annotations.ApiOperation;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final TokenProvider tokenProvider;

    // yesun-23.08.21
    @ApiOperation(value = "사용자 회원가입", notes = "이메일, 닉네임, 전화번호, 비밀번호로 회원가입합니다.")
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody @Valid SignupForm signupForm) {
        authService.signup(SignupDto.from(signupForm));
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    // yesun-23.08.21
    @ApiOperation(value = "공통 로그인", notes = "회원가입 시 입력한 이메일, 비밀번호로 로그인합니다.")
    @PostMapping("/signin")
    public ResponseEntity<SigninForm.Response> signin(
        @RequestBody @Valid SigninForm.Request signinForm
    ) {
        Response signinDto = authService.signin(Request.from(signinForm));
        String accessToken = tokenProvider.generateToken(
            signinDto.getMemberId(), signinDto.getEmail(), signinDto.getRole()
        );
        return ResponseEntity.status(HttpStatus.OK)
            .body(SigninForm.Response.builder()
                .token(accessToken)
                .build());
    }
}