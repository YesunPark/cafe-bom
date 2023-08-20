package com.zerobase.CafeBom.config;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// 로그인 실패했을때
public class UserAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
        AuthenticationException exception) throws IOException, ServletException {

        setUseForward(true);
        setDefaultFailureUrl("/login?error=true");
        request.setAttribute("errorMessage", "로그인에 실패하였습니다.");

        super.onAuthenticationFailure(request, response, exception);
    }
}
