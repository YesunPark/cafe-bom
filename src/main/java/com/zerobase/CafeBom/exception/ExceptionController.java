package com.zerobase.cafebom.exception;

import com.zerobase.cafebom.type.ErrorCode;
import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
@RestController
@Slf4j
public class ExceptionController {

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> dtaIntegrityViolationException(
        final DataIntegrityViolationException e
    ) {
        log.warn("DataIntegrityViolationException 발생!!! trace:{}",
            e.getStackTrace());
//        ExceptionResponse errorResponse = makeExceptionResponse(e.getBindingResult());
//
//        BindingResult bindingResult = e.getBindingResult();

        StringBuilder builder = new StringBuilder();
//        for (FieldError fieldError : bindingResult.getFieldErrors()) {
//            builder.append("[");
//            builder.append(fieldError.getField());
//            builder.append("](은)는 ");
//            builder.append(fieldError.getDefaultMessage());
//            builder.append(" 입력된 값: [");
//            builder.append(fieldError.getRejectedValue());
//            builder.append("]");
//        }

        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(builder.toString());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> methodValidException(final MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();

        StringBuilder builder = new StringBuilder();
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            builder.append(fieldError.getDefaultMessage());
            builder.append(" 입력된 값: [");
            builder.append(fieldError.getRejectedValue());
            builder.append("]");
        }

        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(builder.toString());
    }

    @ExceptionHandler(CustomException.class)
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

    //    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValidException(
        MethodArgumentNotValidException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(ExceptionResponse.builder()
//                .errorCode(e.getMessage())
                .errorMessage(e.getBindingResult().getFieldErrors().get(0).getDefaultMessage())
                .build());

//        .e.getBindingResult().getFieldErrors().get(0).getDefaultMessage() ,HttpStatus.BAD_REQUEST);
//
//
//        ResponseEntity
//            .status(c.getErrorStatus())
//            .body(ExceptionResponse.builder()
//                .errorCode(c.getErrorCode())
//                .errorMessage(c.getErrorMessage())
//                .build()
//            );
    }

//
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ResponseEntity<?> handleMethodArgumentNotValidException(
//        MethodArgumentNotValidException exception) {
//
//        BindingResult bindingResult = exception.getBindingResult();
//        StringBuilder builder = new StringBuilder();
//
//        for (FieldError fieldError : bindingResult.getFieldErrors()) {
//            builder.append(fieldError.getDefaultMessage());
//        }
//
//        log.error("BaseException errorMsg(): {}", builder.toString());
//
////        ExceptionResponse apiException = new ExceptionResponse(
////            , builder.toString()
////        );
//
//        return ResponseEntity.status(c.getErrorStatus())
//            .body(ExceptionResponse.builder()
//                .errorCode(c.getErrorCode())
//                .errorMessage(c.getErrorMessage())
//                .build()
//            );
//    }

    @Getter
    @Builder
    public static class ExceptionResponse {

        private ErrorCode errorCode;
        private String errorMessage;
    }
}