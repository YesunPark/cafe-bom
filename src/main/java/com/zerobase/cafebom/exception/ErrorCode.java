package com.zerobase.cafebom.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    // Auth
    MEMBER_NOT_EXISTS("존재하지 않는 회원입니다.", HttpStatus.BAD_REQUEST),
    EMAIL_ALREADY_EXISTS("이미 존재하는 이메일입니다.", HttpStatus.BAD_REQUEST),
    NICKNAME_ALREADY_EXISTS("이미 존재하는 닉네임입니다.", HttpStatus.BAD_REQUEST),
    PHONE_ALREADY_EXISTS("이미 존재하는 휴대전화번호입니다.", HttpStatus.BAD_REQUEST),
    PASSWORD_NOT_MATCH("비밀번호가 일치하지 않습니다.", HttpStatus.BAD_REQUEST),

    // Orders
    ORDERS_NOT_FOUND("해당 주문이 없습니다.", HttpStatus.NOT_FOUND),
    ORDERS_NOT_COOKING_STATUS("조리 중인 주문이 아닙니다.", HttpStatus.BAD_REQUEST),
    ORDERS_NOT_CORRECT("올바른 주문 변경이 아닙니다.", HttpStatus.BAD_REQUEST),

    // Product
    PRODUCT_NOT_EXISTS("존재하지 않는 상품입니다.", HttpStatus.BAD_REQUEST),
    METHOD_ARGUMENT_TYPE_MISMATCH("메서드 매개변수 타입이 맞지 않습니다.", HttpStatus.BAD_REQUEST),

    // ProductService

    INVALID_INPUT("잘못된 입력입니다", HttpStatus.BAD_REQUEST),

    PRODUCTCATEGORY_NOT_FOUND("존재하지 않는 상품 카테고리입니다.", HttpStatus.BAD_REQUEST),

    // Cart
    CART_IS_EMPTY("장바구니에 담긴 상품이 없습니다.", HttpStatus.BAD_REQUEST),

    // ProductCategory
    NOT_FOUND_PRODUCT_CATEGORY("상품 카테고리가 존재하지 않습니다.", HttpStatus.BAD_REQUEST),

    // Option
    OPTION_NOT_EXISTS("존재하지 않는 옵션입니다.", HttpStatus.BAD_REQUEST),
    NOT_FOUND_OPTION("옵션을 찾을 수 없습니다.", HttpStatus.BAD_REQUEST),
    NOT_FOUND_OPTION_CATEGORY("옵션 카테고리를 찾을 수 없습니다.", HttpStatus.BAD_REQUEST),
    NOT_FOUND_OPTION_ID("옵션을 찾을 수 없습니다.", HttpStatus.BAD_REQUEST);

    private final String message;
    private final HttpStatus httpStatus;
}