package com.juseungl.moduleexternalapi.oauth.handler;

import com.juseungl.modulecommon.response.ApiResponse;
import com.juseungl.modulecommon.utils.HttpResponseUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static com.juseungl.moduleexternalapi.oauth.exception.JwtErrorCode.FORBIDDEN_ACCESS;

/**
 * 인가 과정에서 생길 exception을 처리
 * 인증된 사용자이지만 해당 리소스에 접근할 권한이 없는 경우
 */
@Slf4j
@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        log.info("JwtAccessDeniedHandler: AccessDeniedException", accessDeniedException);
        ResponseEntity<ApiResponse<Object>> fail = ApiResponse.fail(FORBIDDEN_ACCESS.getHttpStatus(), FORBIDDEN_ACCESS.getMessage());
        HttpResponseUtil.setErrorResponse(response, HttpStatus.FORBIDDEN, fail);
    }
}