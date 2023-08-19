package com.zerobase.CafeBom.exception;

import com.zerobase.CafeBom.type.ErrorCode;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class ExceptionController {

    @ExceptionHandler({CustomException.class})
    public ResponseEntity<ExceptionResponse> customRequestException(final CustomException c) {
        log.error("Api Exception => {}", c.getErrorCode());
        return ResponseEntity
            .status(c.getErrorStatus())
            .body(ExceptionResponse.builder()
                .errorCode(c.getErrorCode())
                .errorMessage(c.getErrorMessage())
                .build()
            );
    }

    @Getter
    @Builder
    public static class ExceptionResponse {

        private ErrorCode errorCode;
        private String errorMessage;
    }
}