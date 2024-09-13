package com.juseungl.moduleexternalapi.oauth.handler;

import com.juseungl.moduleexternalapi.oauth.exception.JwtCustomException;
import com.juseungl.moduleexternalapi.oauth.exception.JwtErrorCode;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 인증 과정에서 생길 exception을 처리
 * 로그인이 필요한 API에 인증 정보 없이 접근할 때 이 핸들러가 호출
 */
@Slf4j
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        log.debug("JwtAuthenticationEntryPoint");
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getLocalizedMessage());
    }
}