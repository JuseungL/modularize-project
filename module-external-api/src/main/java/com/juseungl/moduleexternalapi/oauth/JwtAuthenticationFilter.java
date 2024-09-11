package com.juseungl.moduleexternalapi.oauth;

import com.juseungl.modulecommon.jwt.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter{
    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {

        String jwt = jwtUtil.parseToken(request);

        if (jwt != null) {
            jwtUtil.validateToken(jwt);
            setAuthentication(jwt);
        }

        chain.doFilter(request, response);
    }

    private void setAuthentication(String accessToken) {
//        Authentication authentication = jwtUtil.resolveToken(accessToken);
//        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

}