package com.zerobase.cafebom.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    // 컨트롤러 @PathVariable TypeMismatch
    METHOD_ARGUMENT_TYPE_MISMATCH("메서드 매개변수의 타입이 맞지 않습니다.", BAD_REQUEST),

    // Auth
    MEMBER_NOT_EXISTS("존재하지 않는 회원입니다.", BAD_REQUEST),
    EMAIL_ALREADY_EXISTS("이미 존재하는 이메일입니다.", BAD_REQUEST),
    NICKNAME_ALREADY_EXISTS("이미 존재하는 닉네임입니다.", BAD_REQUEST),
    PHONE_ALREADY_EXISTS("이미 존재하는 휴대전화번호입니다.", BAD_REQUEST),
    PASSWORD_NOT_MATCH("비밀번호가 일치하지 않습니다.", BAD_REQUEST),
    ADMIN_CODE_NOT_MATCH("관리자 인증코드가 일치하지 않습니다.", BAD_REQUEST),

    // Orders
    ORDERS_NOT_EXISTS("존재하지 않는 주문입니다.", NOT_FOUND),
    ORDERS_ALREADY_COOKING_STATUS("이미 조리 중인 주문입니다.", BAD_REQUEST),
    ORDERS_NOT_COOKING_STATUS("조리 중인 주문이 아닙니다.", BAD_REQUEST),
    ORDERS_NOT_CORRECT("주문 상태는 이전 상태로 변경이 불가능합니다.", BAD_REQUEST),
    ORDERS_ALREADY_CANCELED("이미 취소된 주문입니다.", BAD_REQUEST),
    ORDERS_NOT_RECEIVED_STATUS("수락되지 않은 주문입니다.", BAD_REQUEST),
    ORDERS_COOKING_TIME_ALREADY_SET("이미 조리 예정 시간이 설정되어 있습니다.", BAD_REQUEST),
    START_DATE_AND_END_DATE_ARE_ESSENTIAL("시작 날짜와 끝 날짜를 입력해야 합니다.", BAD_REQUEST),

    // Product
    PRODUCT_NOT_EXISTS("존재하지 않는 상품입니다.", BAD_REQUEST),

    // Cart
    CART_IS_EMPTY("장바구니에 담긴 상품이 없습니다.", BAD_REQUEST),

    // ProductCategory
    PRODUCTCATEGORY_NOT_EXISTS("존재하지 않는 상품 카테고리입니다.", BAD_REQUEST),

    // Option
    OPTION_NOT_EXISTS("존재하지 않는 옵션입니다.", BAD_REQUEST),
    NOT_FOUND_OPTION_CATEGORY("옵션 카테고리를 찾을 수 없습니다.", BAD_REQUEST);

    private final String message;
    private final HttpStatus httpStatus;
}