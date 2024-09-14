package com.juseungl.moduleexternalapi.oauth.jwt;

import com.juseungl.modulecommon.exception.CommonErrorCode;
import com.juseungl.modulecommon.exception.JwtErrorCode;
import com.juseungl.modulecommon.response.ApiResponse;
import com.juseungl.modulecommon.utils.HttpResponseUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    private final HttpResponseUtil httpResponseUtil;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        String jwtToken = jwtUtil.parseToken(request);

        if (jwtToken != null) {
            try {
                log.info("토큰 검증 시작");
                jwtUtil.validateTokenV2(jwtToken);
                log.info("토큰 검증 종료");
                setAuthentication(jwtToken);
            } catch (SecurityException | MalformedJwtException e) {
                log.warn("잘못된 토큰: {}", e.getMessage());
                httpResponseUtil.setErrorResponse(response, HttpStatus.UNAUTHORIZED, ApiResponse.fail(JwtErrorCode.INVALID_TOKEN.getHttpStatus(), "잘못된 토큰: " + e.getMessage()));
                return;
            } catch (ExpiredJwtException e) {
                log.warn("만료된 토큰: {}", e.getMessage());
                httpResponseUtil.setErrorResponse(response, HttpStatus.UNAUTHORIZED, ApiResponse.fail(JwtErrorCode.TOKEN_EXPIRED.getHttpStatus(), "만료된 토큰: " + e.getMessage()));
                return;
            } catch (UnsupportedJwtException e) {
                log.warn("지원하지 않는 토큰: {}", e.getMessage());
                httpResponseUtil.setErrorResponse(response, HttpStatus.UNAUTHORIZED, ApiResponse.fail(JwtErrorCode.INVALID_TOKEN.getHttpStatus(), "지원하지 않는 토큰: " + e.getMessage()));
                return;
            } catch (IllegalArgumentException e) {
                log.warn("잘못된 토큰 인수: {}", e.getMessage());
                httpResponseUtil.setErrorResponse(response, HttpStatus.UNAUTHORIZED, ApiResponse.fail(JwtErrorCode.INVALID_TOKEN.getHttpStatus(), "잘못된 토큰 인수: " + e.getMessage()));
                return;
            } catch (UsernameNotFoundException e) {
                // 이 예외는 토큰 검증이 아닌 사용자 조회 과정에서 발생할 수 있으므로, 별도로 처리합니다.
                log.warn("사용자를 찾을 수 없음: {}", e.getMessage());
                httpResponseUtil.setErrorResponse(response, HttpStatus.UNAUTHORIZED, ApiResponse.fail(JwtErrorCode.NOT_FOUND_MEMBER.getHttpStatus(), "사용자를 찾을 수 없습니다: " + e.getMessage()));
                return;
            } catch (Exception e) {
                log.error("예상치 못한 오류 발생: {}", e.getMessage());
                httpResponseUtil.setErrorResponse(response, HttpStatus.INTERNAL_SERVER_ERROR, ApiResponse.fail(CommonErrorCode.INTERNAL_SERVER_ERROR.getHttpStatus(), "예상치 못한 오류 발생: " + e.getMessage()));
                return;
            }
        }
        filterChain.doFilter(request, response);
    }

    private void setAuthentication(String jwtToken) {
        Authentication authentication = jwtUtil.resolveToken(jwtToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
