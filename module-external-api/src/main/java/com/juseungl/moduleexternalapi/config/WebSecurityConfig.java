package com.juseungl.moduleexternalapi.config;

import com.juseungl.moduleexternalapi.oauth.handler.JwtAccessDeniedHandler;
import com.juseungl.moduleexternalapi.oauth.handler.JwtAuthenticationEntryPoint;
import com.juseungl.moduleexternalapi.oauth.handler.OAuth2AuthenticationFailureHandler;
import com.juseungl.moduleexternalapi.oauth.handler.OAuth2AuthenticationSuccessHandler;
import com.juseungl.moduleexternalapi.oauth.jwt.JwtAuthenticationFilter;
import com.juseungl.moduleexternalapi.oauth.service.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;
    private final OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;
    private final OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)// UsernamePasswordAuthenticationFilter.class 실행X. eo
                .logout(AbstractHttpConfigurer::disable)
                .headers(c -> c.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable).disable())
                .sessionManagement(c ->c.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(request ->
                        request.requestMatchers(
                                        new AntPathRequestMatcher("/"),
                                        new AntPathRequestMatcher("/actuator/health"),
                                        new AntPathRequestMatcher("/oauth2/authorization/**"),
                                        new AntPathRequestMatcher("/login/oauth2/code/**"),
                                        new AntPathRequestMatcher("/oauth2/**"),
                                        new AntPathRequestMatcher("/api/v1/public/**"),
                                        new AntPathRequestMatcher("/batch-execute")
                                ).permitAll() // 위의 경로들은 인증 없이 접근 가능
                                .anyRequest().authenticated() // 그 외의 모든 요청은 인증 필요
                )
                .oauth2Login(oauth ->
                        oauth.userInfoEndpoint(c -> c.userService(customOAuth2UserService))
                            .successHandler(oAuth2AuthenticationSuccessHandler)
                            .failureHandler(oAuth2AuthenticationFailureHandler)
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(
                        (exceptions) -> exceptions
                                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                                .accessDeniedHandler(jwtAccessDeniedHandler)
                );
        return http.build();
    }
    @Bean // SecurityFilterChain에서 제외
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring()
                .requestMatchers("/error", "/favicon.ico", "/swagger-ui/**", "/api-docs/**");
    }
}
