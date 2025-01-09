package com.mycompany.simpleboard.config.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCodeImpl implements ErrorCode {
    OK(HttpStatus.OK.value(), "성공"),
    BAD_REQUEST(HttpStatus.BAD_REQUEST.value(), "잘못된 요청입니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), "서버 내부에 에러가 발생했습니다."),
    NULL_POINT(HttpStatus.INTERNAL_SERVER_ERROR.value(), "NULL POINT 입니다."),
    INVALID_INPUT_DATA(HttpStatus.BAD_REQUEST.value(), "유효하지 않은 입력값입니다."),
    MISSING_REQUIRED_HEADER(HttpStatus.BAD_REQUEST.value(), "필수 헤더값이 누락 또는 만료되었습니다."),
    MISSING_REQUIRED_PARAM(HttpStatus.BAD_REQUEST.value(), "필수 파라미터값이 누락되었습니다.")
    ;

    private final Integer httpCode;
    private final String description;
}
