package com.juseungl.moduleexternalapi.oauth.exception;

import com.juseungl.modulecommon.exception.BaseErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.NOT_ACCEPTABLE;
@Getter
@RequiredArgsConstructor
public enum AuthErrorCode implements BaseErrorCode {
    ILLEGAL_REGISTRATION_ID(NOT_ACCEPTABLE, "잘못된 Registration ID 입니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
