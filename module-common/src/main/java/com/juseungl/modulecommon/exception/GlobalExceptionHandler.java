package com.juseungl.modulecommon.exception;

import com.juseungl.modulecommon.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

/**
 * Controller Layer에서 발생하는 예외를 처리하는 것이지
 * Filter 단의 예외는 인식하지 못한다...!
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ApiResponse<Object>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        return ApiResponse.fail(CommonErrorCode.INVALID_PARAMETER.getHttpStatus(), e.getMessage());
    }

    @ExceptionHandler(NoSuchElementException.class)
    protected ResponseEntity<ApiResponse<Void>> handleNoSuchElementException(NoSuchElementException e) {
        return ApiResponse.fail(CommonErrorCode.NOT_FOUND.getHttpStatus(), e.getMessage());
    }

    @ExceptionHandler(JwtCustomException.class)
    public ResponseEntity<ApiResponse<Void>> handleJwtCustomException(JwtCustomException ex) {
        HttpStatus status = ex.getBaseErrorCode().getHttpStatus();
        String message = ex.getMessage();
        return ApiResponse.fail(status, message);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleException(Exception ex) {
        return ApiResponse.fail(CommonErrorCode.INTERNAL_SERVER_ERROR.getHttpStatus(), ex.getMessage());
    }
}
