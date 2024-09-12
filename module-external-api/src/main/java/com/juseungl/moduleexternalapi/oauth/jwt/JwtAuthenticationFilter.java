package com.juseungl.moduleexternalapi.oauth.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter{
    private final JwtUtil jwtUtil;
    @Override
    protected void doFilterInternal(
        @NonNull HttpServletRequest request,
        @NonNull HttpServletResponse response,
        @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        String jwtToken = jwtUtil.parseToken(request);

        if (jwtToken != null) {
            jwtUtil.validateToken(jwtToken);
            setAuthentication(jwtToken);
        }
        filterChain.doFilter(request, response);
    }

    private void setAuthentication(String jwtToken) {
        Authentication authentication = jwtUtil.resolveToken(jwtToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}