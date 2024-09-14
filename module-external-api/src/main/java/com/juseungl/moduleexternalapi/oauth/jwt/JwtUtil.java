package com.juseungl.moduleexternalapi.oauth.jwt;

import com.juseungl.modulecommon.exception.JwtCustomException;
import com.juseungl.modulecommon.exception.JwtErrorCode;
import com.juseungl.modulecommon.utils.RedisUtil;
import com.juseungl.moduleexternalapi.oauth.dto.JwtResponseDto;
import com.juseungl.moduleexternalapi.oauth.service.CustomUserDetailsService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.juseungl.moduleexternalapi.oauth.jwt.JwtProperties.AUTHORITIES;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtUtil {
    private final CustomUserDetailsService customUserDetailsService;
    private final JwtProperties jwtProperties;
    private final RedisUtil redisUtil;
    private SecretKey secretKey;

    @PostConstruct
    public void afterPropertiesSet() {
        byte[] decoded = Decoders.BASE64.decode(jwtProperties.getSecretKey());
        this.secretKey = Keys.hmacShaKeyFor(decoded);
    }

    public String parseToken(HttpServletRequest request) {
        String header = request.getHeader(JwtProperties.AUTHORIZATION_HEADER);
        if (header == null || !header.startsWith(JwtProperties.BEARER_PREFIX)) {
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

    public boolean validateTokenV2(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            // 예외 처리 로직을 필터로 이동하였으므로, 여기서는 예외를 그대로 던집니다.
            throw e;
        }
    }



    public JwtResponseDto issueToken(Authentication authentication) {
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        Instant issuedAt = Instant.now().truncatedTo(ChronoUnit.SECONDS);

        Date accessExpiration = Date.from(issuedAt.plus(jwtProperties.getAccessExpiredTime(), ChronoUnit.MILLIS));
        Date refreshExpiration = Date.from(issuedAt.plus(jwtProperties.getRefreshExpiredTime(), ChronoUnit.MILLIS));
        Date issuedDate = new Date();
        String memberId = authentication.getName();
        var accessToken = Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setIssuer(jwtProperties.getIssuer())
                .setIssuedAt(issuedDate)
                .setExpiration(accessExpiration)
                .setSubject(memberId)
                .signWith(secretKey, SignatureAlgorithm.HS512)
                .claim(AUTHORITIES, authorities)
                .compact();

        var refreshToken = Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setIssuer(jwtProperties.getIssuer())
                .setIssuedAt(issuedDate)
                .setExpiration(refreshExpiration)
                .setSubject(memberId)
                .signWith(secretKey, SignatureAlgorithm.HS512)
                .claim(AUTHORITIES, authorities)
                .compact();

        redisUtil.saveAsValue(memberId, refreshToken, jwtProperties.getRefreshExpiredTime(), TimeUnit.MILLISECONDS);
        return new JwtResponseDto(accessToken, refreshToken);
    }

    public Authentication resolveToken(String token) {

        JwtParser jwtParser = Jwts.parser().setSigningKey(secretKey).build();
        Claims claims = jwtParser.parseClaimsJws(token).getBody();

        Collection<SimpleGrantedAuthority> authorities = Stream.of(
                        String.valueOf(claims.get(AUTHORITIES)).split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        UserDetails userDetails = // 반환 결과 memberId를 바탕으로 유저 정보를 담은 UserDetails객체(Principal 객체) 반환
                customUserDetailsService.loadUserByUsername(
                    claims.getSubject() // JWT에 유저 식별자(Subject)로 MemberId를 담았음
                );
        return new UsernamePasswordAuthenticationToken(userDetails, token, authorities);
    }

}