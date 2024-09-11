package com.juseungl.modulecommon.exception.global;


import com.juseungl.modulecommon.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

@Component
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 400
     * @Valid 결과 유효하지 않다고 판단된 경우 예외 처리
     * Request Body 자체는 넘어왔지만 안에 값들의 @Valid 검증 중 유효하지 않은 필드값 발생 시 예외 처리
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ApiResponse<Object>> handleMethodArgumentNotValidException(final MethodArgumentNotValidException e) {
        return ApiResponse.fail(CommonErrorCode.INVALID_PARAMETER, e.getMessage());
    }

    /**
     * 404
     * 요청한 리소스를 찾을 수 없는 경우 예외 처리
     */
    @ExceptionHandler(NoSuchElementException.class)
    protected ResponseEntity<ApiResponse<Void>> handleNoSuchElementException(NoSuchElementException e) {
        return ApiResponse.fail(CommonErrorCode.NOT_FOUND, e.getMessage());
    }

    /**
     * 500
     * 그 외의 모든 예외를 처리
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleException(Exception ex) {
        return ApiResponse.fail(CommonErrorCode.INTERNAL_SERVER_ERROR, ex.getMessage());
    }
}

