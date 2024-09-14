package com.juseungl.modulecommon.exception;

import com.juseungl.modulecommon.response.ApiResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
@RequiredArgsConstructor
public enum JwtErrorCode implements BaseErrorCode {
    INVALID_TOKEN(HttpStatus.BAD_REQUEST, "잘못된 형식의 토큰입니다."),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "인증이 필요합니다."),
    TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "토큰이 만료되었습니다."),
    TOKEN_SIGNATURE_ERROR(HttpStatus.UNAUTHORIZED, "토큰이 위조되었거나 손상되었습니다."),
    FORBIDDEN_ACCESS(HttpStatus.FORBIDDEN, "권한이 없습니다."),
    TOKEN_NOT_FOUND(HttpStatus.UNAUTHORIZED, "토큰이 존재하지 않습니다."),
    INTERNAL_SECURITY_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "인증 처리 중 서버 에러가 발생했습니다."),
    INTERNAL_TOKEN_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "토큰 처리 중 서버 에러가 발생했습니다."),

    NOT_FOUND_MEMBER(HttpStatus.NOT_FOUND, "토큰 검증 결과 존재하지 않는 멤버입니다.");

    private final HttpStatus httpStatus;
    private final String message;

    @Override
    public ResponseEntity<ApiResponse<Void>> toResponseEntity() {
        return ApiResponse.fail(httpStatus, message);
    }
}
