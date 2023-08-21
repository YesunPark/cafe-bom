package com.zerobase.cafebom.user.controller.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SignupForm {

    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[~!@#$])[A-Za-z\\d~!@#$]{8,11}$"
        , message = "비밀번호는 영어 대소문자, 숫자, 특수문자(~!@#$)를 포함한 8~11 자리로 입력해야 합니다.")
    @NotBlank(message = "비밀번호는 필수로 입력해야 합니다.")
    private String password;

    @Pattern(regexp = "^[가-힣]*$", message = "닉네임은 한글로 입력해야 합니다.")
    @Size(min = 2, max = 6, message = "닉네임은 2~6 자리로 입력해야 합니다.")
    @NotBlank(message = "닉네임은 필수로 입력해야 합니다.")
    private String nickname;

    @NotBlank(message = "전화번호는 필수로 입력해야 합니다.")
    private String phone;

    @Email(message = "이메일 형식을 확인해주세요.")
    @NotBlank(message = "이메일은 필수로 입력해야 합니다.")
    private String email;
}
