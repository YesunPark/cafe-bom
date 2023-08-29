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
    ORDERS_NOT_FOUND("해당 주문이 없습니다.", HttpStatus.NOT_FOUND),
    ORDERS_ALREADY_COOKING_STATUS("이미 조리 중인 주문입니다.", HttpStatus.BAD_REQUEST),
    ORDERS_NOT_COOKING_STATUS("조리 중인 주문이 아닙니다.", HttpStatus.BAD_REQUEST),
    ORDERS_ALREADY_CANCELED("이미 취소된 주문입니다.", HttpStatus.BAD_REQUEST),
    ORDERS_NOT_RECEIVED_STATUS("수락되지 않은 주문입니다.", HttpStatus.BAD_REQUEST),
    ORDERS_COOKING_TIME_ALREADY_SET("이미 조리 예정 시간이 설정되어 있습니다.", HttpStatus.BAD_REQUEST);

    private final String message;
    private final HttpStatus httpStatus;
}