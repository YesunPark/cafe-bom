package com.zerobase.CafeBom.exception;

import com.zerobase.CafeBom.type.ErrorCode;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {

    private final ErrorCode errorCode;
    private final String errorMessage;

    public CustomException(ErrorCode errorCode) {
        super(errorCode.getDescription()); // 이 부분 ExceptionHandler 로 처리하라고 피드백 받음.
        this.errorCode = errorCode;
        this.errorMessage = errorCode.getDescription();
    }
}