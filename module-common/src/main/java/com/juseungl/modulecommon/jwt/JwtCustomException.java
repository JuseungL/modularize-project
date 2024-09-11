package com.juseungl.modulecommon.jwt;

import com.juseungl.modulecommon.exception.CustomException;
import com.juseungl.modulecommon.response.ApiResponse;
import com.juseungl.modulecommon.exception.BaseErrorCode;
import lombok.Getter;
import org.springframework.http.ResponseEntity;

@Getter
public class JwtCustomException extends CustomException {

    public JwtCustomException(BaseErrorCode errorCode) {
        super(errorCode);
    }

    public JwtCustomException(BaseErrorCode errorCode, String message) {
        super(errorCode, message);
    }

    @Override
    public ResponseEntity<ApiResponse<Void>> toApiResponse() {
        return ApiResponse.fail(getErrorCode(), getMessage());
    }
}
