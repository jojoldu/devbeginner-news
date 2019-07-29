package com.jojoldu.devbeginnernews.web.presentation.api.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum WebResponseStatus {
    OK(HttpStatus.OK, "요청이 성공하였습니다."),

    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "로그인이 필요한 서비스입니다."),
    FORBIDDEN(HttpStatus.FORBIDDEN, "접근이 거부되었습니다."),
    NOT_FOUND(HttpStatus.NOT_FOUND, "해당 정보를 찾을 수 없습니다."),

    SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 에러입니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
