package com.zerobase.cafebom.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    // AuthService
    MEMBER_NOT_EXISTS("존재하지 않는 회원입니다.", HttpStatus.BAD_REQUEST),
    EMAIL_ALREADY_EXISTS("이미 존재하는 이메일입니다.", HttpStatus.BAD_REQUEST),
    NICKNAME_ALREADY_EXISTS("이미 존재하는 닉네임입니다.", HttpStatus.BAD_REQUEST),
    PHONE_ALREADY_EXISTS("이미 존재하는 휴대전화번호입니다.", HttpStatus.BAD_REQUEST),
    PASSWORD_NOT_MATCH("비밀번호가 일치하지 않습니다.", HttpStatus.BAD_REQUEST),
    // OrderService
    ORDER_NOT_FOUND("해당 주문이 없습니다.", HttpStatus.NOT_FOUND),
    COOKING_NOT_CHANGE_NONE("이미 조리 중인 주문입니다.", HttpStatus.BAD_REQUEST),
    NOT_COOKING_ORDER("조리 중인 주문이 아닙니다.", HttpStatus.BAD_REQUEST);

    private final String message;
    private final HttpStatus httpStatus;
}