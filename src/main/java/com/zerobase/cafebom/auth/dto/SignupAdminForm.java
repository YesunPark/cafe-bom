package com.zerobase.cafebom.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Builder
public class SignupAdminForm {

    @Schema(description = "관리자 인증코드", example = "000000")
    @NotBlank(message = "관리자 인증코드는 필수로 입력해야 합니다.")
    @Size(min = 6, max = 6, message = "인증코드는 숫자 6자리입니다.")
    private String adminCode;

    @Schema(description = "관리자 실명", example = "홍길동")
    @NotBlank(message = "관리자 실명은 필수로 입력해야 합니다.")
    @Pattern(regexp = "^[가-힣]{2,4}$", message = "관리자 실명은 한글 2~4자리로 입력해야 합니다.")
    private String adminName;

    @Schema(description = "이메일", example = "admin@cafe.com")
    @Email(message = "이메일 형식을 확인해주세요.")
    @NotBlank(message = "이메일은 필수로 입력해야 합니다.")
    private String email;

    @Schema(description = "비밀번호", example = "admin123!")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[~!@#$])[A-Za-z\\d~!@#$]{8,11}$"
        , message = "비밀번호는 영어 대소문자, 숫자, 특수문자(~!@#$)를 포함한 8~11 자리로 입력해야 합니다.")
    @NotBlank(message = "비밀번호는 필수로 입력해야 합니다.")
    private String password;

    @Schema(description = "휴대전화번호", example = "01012345678")
    @Size(min = 11, max = 13, message = "전화번호는 11~13 자리로 입력해야 합니다.")
    @NotBlank(message = "전화번호는 필수로 입력해야 합니다.")
    private String phone;
}