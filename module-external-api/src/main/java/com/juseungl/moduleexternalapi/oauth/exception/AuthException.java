package com.juseungl.moduleexternalapi.oauth.exception;

import com.juseungl.modulecommon.exception.BaseErrorCode;
import com.juseungl.modulecommon.exception.CustomException;
import com.juseungl.modulecommon.response.ApiResponse;
import org.springframework.http.ResponseEntity;

public class AuthException extends CustomException {
    public AuthException(BaseErrorCode baseErrorCode) {
        super(baseErrorCode);
    }
}
