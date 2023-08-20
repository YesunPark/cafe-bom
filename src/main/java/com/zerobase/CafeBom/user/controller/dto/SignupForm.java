package com.zerobase.CafeBom.user.controller.dto;

import com.zerobase.CafeBom.type.Role;
import com.zerobase.CafeBom.user.domain.entity.Member;
import com.zerobase.CafeBom.user.service.dto.SignupDto;
import javax.crypto.Mac;
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
