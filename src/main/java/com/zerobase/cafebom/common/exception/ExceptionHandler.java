package com.zerobase.cafebom.common.exception;

import static com.zerobase.cafebom.common.exception.ErrorCode.METHOD_ARGUMENT_TYPE_MISMATCH;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
@Slf4j
public class ExceptionHandler {

    // 컨트롤러 DTO validation 핸들러-yesun-23.08.21
    @org.springframework.web.bind.annotation.ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<MethodInvalidResponse> methodInvalidException(
        final MethodArgumentNotValidException e
    ) {
        BindingResult bindingResult = e.getBindingResult();
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(MethodInvalidResponse.builder()
                .errorCode(bindingResult.getFieldErrors().get(0).getCode())
                .errorMessage(bindingResult.getFieldErrors().get(0).getDefaultMessage())
                .build());
    }

    // 컨트롤러 @PathVariable TypeMismatch 핸들러-wooyoung-23.08.24
    @org.springframework.web.bind.annotation.ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ExceptionResponse> methodArgumentTypeMismatchException() {
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(ExceptionResponse.builder()
                .errorCode(METHOD_ARGUMENT_TYPE_MISMATCH)
                .errorMessage(METHOD_ARGUMENT_TYPE_MISMATCH.getMessage())
                .build()
            );
    }

    // CustiomException 핸들러-yesun-23.08.21
    @org.springframework.web.bind.annotation.ExceptionHandler(CustomException.class)
    public ResponseEntity<ExceptionResponse> customRequestException(final CustomException c) {
        log.error("Api Exception => {}, {}", c.getErrorCode(), c.getErrorMessage());
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
    public static class MethodInvalidResponse {

        private String errorCode;
        private String errorMessage;
    }

    @Getter
    @Builder
    public static class ExceptionResponse {

        private ErrorCode errorCode;
        private String errorMessage;
    }
}