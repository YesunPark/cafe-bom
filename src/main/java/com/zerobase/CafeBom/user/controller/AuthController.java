package com.zerobase.CafeBom.user.controller;

import com.zerobase.CafeBom.user.controller.dto.SignupForm;
import com.zerobase.CafeBom.user.service.AuthService;
import com.zerobase.CafeBom.user.service.dto.SignupDto;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    // 회원가입-yesun-23.08.21
    @PostMapping("/signin")
    public ResponseEntity<?> signin(@RequestBody @Valid SignupForm signupForm) {
        authService.signup(SignupDto.from(signupForm));
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
