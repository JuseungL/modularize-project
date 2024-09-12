package com.juseungl.moduleexternalapi.oauth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record JwtResponseDto(
        @JsonProperty("accessToken") String accessToken,
        @JsonProperty("refreshToken") String refreshToken
) {}