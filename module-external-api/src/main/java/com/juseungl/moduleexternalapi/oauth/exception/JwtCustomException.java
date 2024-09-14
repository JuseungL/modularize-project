package com.juseungl.moduleexternalapi.oauth.exception;

import com.juseungl.modulecommon.exception.BaseErrorCode;
import com.juseungl.modulecommon.exception.CustomException;
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
