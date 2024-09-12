package com.juseungl.moduleexternalapi.oauth.handler;

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

@RequiredArgsConstructor
@Component
public class OAuth2AuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtUtil jwtUtil;
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
