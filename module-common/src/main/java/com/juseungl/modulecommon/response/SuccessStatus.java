package com.juseungl.modulecommon.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public enum SuccessStatus {

    /**
     * auth
     */
    TOKEN_REFRESH_SUCCESS(HttpStatus.OK, "토큰 갱신 성공"),
    LOGOUT_SUCCESS(HttpStatus.OK, "로그아웃 성공"),
    ;


    private final HttpStatus httpStatus;
    private final String message;

    public int getStatusCode() {
        return this.httpStatus.value();
    }
}

