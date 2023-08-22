package com.zerobase.cafebom.member.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SignupForm {

    @Schema(description = "비밀번호", example = "test123!")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[~!@#$])[A-Za-z\\d~!@#$]{8,11}$"
        , message = "비밀번호는 영어 대소문자, 숫자, 특수문자(~!@#$)를 포함한 8~11 자리로 입력해야 합니다.")
    @NotBlank(message = "비밀번호는 필수로 입력해야 합니다.")
    private String password;

    @Schema(description = "닉네임", example = "커피조아")
    @Pattern(regexp = "^[가-힣]*$", message = "닉네임은 한글로 입력해야 합니다.")
    @Size(min = 2, max = 6, message = "닉네임은 2~6 자리로 입력해야 합니다.")
    @NotBlank(message = "닉네임은 필수로 입력해야 합니다.")
    private String nickname;

    @Schema(description = "휴대전화번호", example = "01012345678")
    @Size(min = 11, max = 13, message = "전화번호는 11~13 자리로 입력해야 합니다.")
    @NotBlank(message = "전화번호는 필수로 입력해야 합니다.")
    private String phone;

    @Schema(description = "이메일", example = "test@cafe.com")
    @Email(message = "이메일 형식을 확인해주세요.")
    @NotBlank(message = "이메일은 필수로 입력해야 합니다.")
    private String email;
}