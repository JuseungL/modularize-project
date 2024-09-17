package com.juseungl.modulecommon.exception;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {
    private final BaseErrorCode baseErrorCode;
    private final String message;

    public CustomException(BaseErrorCode baseErrorCode) {
        super(baseErrorCode.getMessage());
        this.baseErrorCode = baseErrorCode;
        this.message = baseErrorCode.getMessage();
    }

    public CustomException(BaseErroCode baseErrorCode, String message) {
        super(message);
        this.baseErrorCode = baseErrorCode;
        this.message = message;
    }
}
