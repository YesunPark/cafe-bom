package com.zerobase.CafeBom.type;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    EXAMPLE("예시입니다. 처음 추가하는 분이 삭제해주세요.", HttpStatus.BAD_REQUEST);

    private final String description;
    private final HttpStatus httpStatus;
}