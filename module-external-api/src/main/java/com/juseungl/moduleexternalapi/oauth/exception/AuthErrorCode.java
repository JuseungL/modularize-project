package com.juseungl.moduleexternalapi.oauth.exception;

import com.juseungl.modulecommon.exception.BaseErrorCode;
import com.juseungl.modulecommon.response.ApiResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import static org.springframework.http.HttpStatus.NOT_ACCEPTABLE;
@Getter
@RequiredArgsConstructor
public enum AuthErrorCode implements BaseErrorCode {
    UNAUTHORIZED_MEMBER(UNAUTHORIZED, "인증되지 않은 유저입니다. 로그인 후 이용해주세요."),
    ILLEGAL_REGISTRATION_ID(NOT_ACCEPTABLE, "잘못된 Registration ID 입니다.");


    private final HttpStatus httpStatus;
    private final String message;

    @Override
    public ResponseEntity<ApiResponse<Void>> toResponseEntity() {
        return ApiResponse.fail(httpStatus, message);
    }
}
