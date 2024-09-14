package com.juseungl.modulecommon.exception;

import com.juseungl.modulecommon.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public interface BaseErrorCode {

    HttpStatus getHttpStatus();
    String getMessage();

    ResponseEntity<ApiResponse<Void>> toResponseEntity();
}