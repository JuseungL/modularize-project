package com.juseungl.modulecommon.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.juseungl.modulecommon.exception.BaseErrorCode;
import lombok.*;
import org.springframework.http.ResponseEntity;

@Builder
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ApiResponse<T> {

    private final int value;
    private final boolean success;
    private final String message;
    private T data;

//    public static <T> ResponseEntity<ApiResponse<T>> success(SuccessStatus successStatus) {
//        return ResponseEntity.status(successStatus.getHttpStatus())
//                .body(ApiResponse.<T>builder()
//                        .value(successStatus.getStatusCode())
//                        .success(true)
//                        .message(successStatus.getMessage()).build());
//    }
//
//    public static <T> ResponseEntity<ApiResponse<T>> success(SuccessStatus successStatus, T data) {
//        return ResponseEntity.status(successStatus.getHttpStatus())
//                .body(ApiResponse.<T>builder()
//                        .value(successStatus.getStatusCode())
//                        .success(true)
//                        .message(successStatus.getMessage())
//                        .data(data).build());
//    }


//    public static <T> ResponseEntity<ApiResponse<T>> fail(BaseErrorCode errorCode) {
//        return ResponseEntity.status(errorCode.getHttpStatus())
//                .body(ApiResponse.<T>builder()
//                        .value(errorCode.getHttpStatus().value())
//                        .success(false)
//                        .message(errorCode.getMessage())
//                        .build());
//    }

    public static <T> ResponseEntity<ApiResponse<T>> fail(BaseErrorCode errorCode, String message) {
        return ResponseEntity.status(errorCode.getHttpStatus())
                .body(ApiResponse.<T>builder()
                        .value(errorCode.getHttpStatus().value())
                        .success(false)
                        .message(message)
                        .build());
    }
}