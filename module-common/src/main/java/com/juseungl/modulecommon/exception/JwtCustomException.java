package com.juseungl.modulecommon.exception;

import lombok.Getter;

@Getter
public class JwtCustomException extends CustomException {

    public JwtCustomException(BaseErrorCode errorCode) {
        super(errorCode);
    }

    public JwtCustomException(BaseErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}
