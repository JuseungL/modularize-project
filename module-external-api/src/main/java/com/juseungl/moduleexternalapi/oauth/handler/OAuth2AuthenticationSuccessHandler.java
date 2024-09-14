package com.juseungl.moduleexternalapi.oauth.handler;

import com.juseungl.modulecommon.utils.RedisUtil;
import com.juseungl.moduleexternalapi.oauth.dto.JwtResponseDto;
import com.juseungl.moduleexternalapi.oauth.jwt.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

/**
 *  OAuth2 로그인 성공 시 호출되는 handler
 */
@RequiredArgsConstructor
@Component
public class OAuth2AuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private final JwtUtil jwtUtil;
    private final RedisUtil redisUtil;
    @Value("${spring.security.oauth2.client.callback-uri}")
    private String oauth2CallbackUri;
    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException {
        JwtResponseDto jwtResponseDto = jwtUtil.issueToken(authentication);

        String redirectUrl = UriComponentsBuilder
                .fromUriString(oauth2CallbackUri)
                .queryParam("accessToken", jwtResponseDto.accessToken())
                .queryParam("refreshToken", jwtResponseDto.refreshToken())
                .build()
                .toUriString();

        response.sendRedirect(redirectUrl);
    }
}
