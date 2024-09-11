package com.juseungl.modulecommon.exception;

import com.juseungl.modulecommon.response.ApiResponse;
import lombok.Getter;
import org.springframework.http.ResponseEntity;

@Getter
public class CustomException extends RuntimeException {
    private final BaseErrorCode errorCode;
    private final String message;

    public CustomException(BaseErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
        this.message = errorCode.getMessage();
    }

    public CustomException(BaseErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
        this.message = message;
    }

    public ResponseEntity<ApiResponse<Void>> toApiResponse() {
        return ApiResponse.fail(errorCode, message);
    }
}
