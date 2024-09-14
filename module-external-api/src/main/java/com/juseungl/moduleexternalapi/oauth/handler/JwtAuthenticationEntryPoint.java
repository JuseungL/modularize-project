package com.juseungl.moduleexternalapi.oauth.handler;

import com.juseungl.modulecommon.response.ApiResponse;
import com.juseungl.modulecommon.utils.HttpResponseUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static com.juseungl.moduleexternalapi.oauth.exception.AuthErrorCode.UNAUTHORIZED_MEMBER;

/**
 * 인증 과정에서 생길 exception을 처리
 * 로그인이 필요한 API에 인증 정보 없이 접근할 때 이 핸들러가 호출
 */
@Slf4j
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        log.error("[*] AuthenticationException: ", authException);
        ResponseEntity<ApiResponse<Object>> fail = ApiResponse.fail(UNAUTHORIZED_MEMBER.getHttpStatus(), UNAUTHORIZED_MEMBER.getMessage());
        HttpResponseUtil.setErrorResponse(response, HttpStatus.UNAUTHORIZED, fail);
    }
}