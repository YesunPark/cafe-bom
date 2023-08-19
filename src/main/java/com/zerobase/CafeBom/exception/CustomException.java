package com.zerobase.CafeBom.exception;

import com.zerobase.CafeBom.type.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CustomException extends RuntimeException {

    private final ErrorCode errorCode;
    private final String errorMessage;
    private final HttpStatus errorStatus;

    public CustomException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
        this.errorMessage = errorCode.getMessage();
        this.errorStatus = errorCode.getHttpStatus();
    }
}