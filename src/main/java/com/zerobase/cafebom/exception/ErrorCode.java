package com.zerobase.cafebom.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    // ProductController
    METHOD_ARGUMENT_TYPE_MISMATCH("메서드 매개변수 타입이 맞지 않습니다.", HttpStatus.BAD_REQUEST),

    // ProductService
    PRODUCTCATEGORY_NOT_FOUND("존재하지 않는 상품 카테고리입니다.", HttpStatus.BAD_REQUEST);

    private final String message;
    private final HttpStatus httpStatus;
}