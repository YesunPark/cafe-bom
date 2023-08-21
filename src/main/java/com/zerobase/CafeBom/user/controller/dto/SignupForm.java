package com.zerobase.CafeBom.user.controller.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SignupForm {

    @NotBlank(message = "비밀번호는 필수로 입력해야합니다.")
    private String password;

    @NotBlank(message = "닉네임은 필수로 입력해야합니다.")
    private String nickname;

    @NotBlank(message = "전화번호는 필수로 입력해야합니다.")
    private String phone;

    @Email(message = "이메일 형식을 확인해주세요.")
    private String email;
}
