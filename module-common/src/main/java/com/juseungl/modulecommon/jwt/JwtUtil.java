package com.juseungl.modulecommon.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

import static com.juseungl.modulecommon.jwt.JwtProperties.AUTHORIZATION_HEADER;
import static com.juseungl.modulecommon.jwt.JwtProperties.BEARER_PREFIX;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtUtil {
    private final JwtProperties jwtProperties;
    private SecretKey secretKey;
    @PostConstruct
    public void afterPropertiesSet() {
        byte[] decoded = Decoders.BASE64.decode(jwtProperties.getSecretKey());
        this.secretKey = Keys.hmacShaKeyFor(decoded);
    }

    public String parseToken(HttpServletRequest request) {
        String header = request.getHeader(AUTHORIZATION_HEADER);

        if (header == null || !header.startsWith(BEARER_PREFIX)) {
            return null;
        } else {
            return header.split(" ")[1];
        }
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token);

            return true;
        } catch (SecurityException | MalformedJwtException e) {
            // Log the exception and throw a custom exception with an appropriate error code
            log.warn("Invalid token: {}", e.getMessage());
            throw new JwtCustomException(JwtErrorCode.INVALID_TOKEN, "Invalid token: " + e.getMessage());
        } catch (ExpiredJwtException e) {
            // Log the exception and throw a custom exception with an appropriate error code
            log.warn("Expired token: {}", e.getMessage());
            throw new JwtCustomException(JwtErrorCode.TOKEN_EXPIRED, "Expired token: " + e.getMessage());
        } catch (UnsupportedJwtException e) {
            // Log the exception and throw a custom exception with an appropriate error code
            log.warn("Unsupported token: {}", e.getMessage());
            throw new JwtCustomException(JwtErrorCode.INVALID_TOKEN, "Token not supported: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            // Log the exception and throw a custom exception with an appropriate error code
            log.warn("Invalid token argument: {}", e.getMessage());
            throw new JwtCustomException(JwtErrorCode.INVALID_TOKEN, "Invalid token argument: " + e.getMessage());
        }
    }
}