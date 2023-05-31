package com.sunkyuj.douner.errors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    REGISTER_ID_ALREADY_EXIST (HttpStatus.BAD_REQUEST, "이미 존재하는 아이디입니다."),
    LOGIN_ID_NOT_EXIST (HttpStatus.BAD_REQUEST, "해당 아이디가 존재하지 않습니다."),
    LOGIN_PASSWORD_NOT_EQUAL (HttpStatus.BAD_REQUEST, "아이디와 비밀번호가 일치하지 않습니다."),
    INVALID_FORMAT_TOKEN (HttpStatus.BAD_REQUEST, "jwt 토큰의 형식이 유효하지 않습니다."),
    EXPIRED_ACCESS_TOKEN (HttpStatus.UNAUTHORIZED, "만료된 jwt 토큰입니다."),
    POST_MEMBER_ID_NOT_EXIST (HttpStatus.BAD_REQUEST, "해당 아이디가 존재하지 않습니다."),
    POST_MODIFY_MEMBER_ID_UNAUTHORIZED (HttpStatus.UNAUTHORIZED, "본인이 작성한 게시글만 수정할 수 있습니다.");

    private final HttpStatus status;
    private final String message;
}