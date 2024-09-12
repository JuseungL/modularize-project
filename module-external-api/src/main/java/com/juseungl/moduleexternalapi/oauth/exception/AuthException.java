package com.juseungl.moduleexternalapi.oauth.exception;

import com.juseungl.modulecommon.exception.BaseErrorCode;
import com.juseungl.modulecommon.exception.CustomException;

public class AuthException extends CustomException {
    public AuthException(BaseErrorCode baseErrorCode) {
        super(baseErrorCode);
    }
}