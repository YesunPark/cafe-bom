package com.zerobase.cafebom.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    // HttpStatus.BAD_REQUEST 에서 static import로 수정. =>  타팀에서 멘토님께 코드리뷰 받은 부분

    // Auth
    MEMBER_NOT_EXISTS("존재하지 않는 회원입니다.", BAD_REQUEST),
    EMAIL_ALREADY_EXISTS("이미 존재하는 이메일입니다.", BAD_REQUEST),
    NICKNAME_ALREADY_EXISTS("이미 존재하는 닉네임입니다.", BAD_REQUEST),
    PHONE_ALREADY_EXISTS("이미 존재하는 휴대전화번호입니다.", BAD_REQUEST),
    PASSWORD_NOT_MATCH("비밀번호가 일치하지 않습니다.", BAD_REQUEST),

    // Orders
    ORDERS_NOT_EXISTS("존재하지 않는 주문입니다.", NOT_FOUND),
    ORDERS_NOT_COOKING_STATUS("조리 중인 주문이 아닙니다.", BAD_REQUEST),
    ORDERS_NOT_CORRECT("올바른 주문 변경이 아닙니다.", BAD_REQUEST), // 무슨 뜻?

    // Product
    PRODUCT_NOT_EXISTS("존재하지 않는 상품입니다.", BAD_REQUEST),
    METHOD_ARGUMENT_TYPE_MISMATCH("메서드 매개변수 타입이 맞지 않습니다.", BAD_REQUEST), // 에러코드에 맞지 않는 에러 형식인 듯

    // ProductService -> ?
    INVALID_INPUT("잘못된 입력입니다", BAD_REQUEST), // 담당자분 누구신가여? 뭐가 잘못된 입력인지 알려줘야 할 듯 합니다!

    // Cart
    CART_IS_EMPTY("장바구니에 담긴 상품이 없습니다.", BAD_REQUEST),

    // ProductCategory
    PRODUCTCATEGORY_NOT_EXISTS("존재하지 않는 상품 카테고리입니다.", BAD_REQUEST),
//    NOT_FOUND_PRODUCT_CATEGORY("상품 카테고리가 존재하지 않습니다.", HttpStatus.BAD_REQUEST), => 위에 걸로 통합 완료

    // Option
    OPTION_NOT_EXISTS("존재하지 않는 옵션입니다.", BAD_REQUEST),
    //    NOT_FOUND_OPTION("옵션을 찾을 수 없습니다.", HttpStatus.BAD_REQUEST), => 위에 걸로 통합 완료
//    NOT_FOUND_OPTION_ID("옵션을 찾을 수 없습니다.", HttpStatus.BAD_REQUEST), => 위에 걸로 통합 완료
    NOT_FOUND_OPTION_CATEGORY("옵션 카테고리를 찾을 수 없습니다.", BAD_REQUEST);

    private final String message;
    private final HttpStatus httpStatus;
}