package com.zerobase.CafeBom.config;

import com.zerobase.CafeBom.type.Role;
import com.zerobase.CafeBom.user.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final AuthService authService;

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    UserAuthenticationFailureHandler getFailureHandler() {
        return new UserAuthenticationFailureHandler();
    }

//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http.csrf().disable();
//        http.headers().frameOptions().sameOrigin();
//
//        http.authorizeRequests()
//            .antMatchers("/signup", "/signin", "product-list/**", "product/**")
//            .permitAll();
//
//        http.authorizeRequests()
//            .antMatchers("/admin/**")
//            .hasAuthority(Role.ROLE_ADMIN.toString());
//
//        http.formLogin()
//            .loginPage("/signin")
//            .failureHandler(getFailureHandler())
//            .permitAll();
//
//        http.exceptionHandling()
//            .accessDeniedPage("/error/denied");
//
//        return http.build();
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.headers().frameOptions().disable();

        http.httpBasic().disable()
            .csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authorizeRequests()
            .antMatchers("/**/signup", "/**/signin","/h2-console/**").permitAll()
            .and()
            .addFilterBefore(this.jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }

    /*
    AuthenticationProvider
    - 인증을 담당하는 인터페이스. 사용자의 인증정보를 확인하고 인증에 성공했을 때 인증 객체를 반환

    AuthService를 통해 사용자 정보를 가져와 비밀번호 일치 여부를 확인하고 사용자의 인증정보를 반환

     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(this.authService);
        provider.setPasswordEncoder(this.getPasswordEncoder());

        return provider;
    }
}