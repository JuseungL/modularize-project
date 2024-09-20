package com.juseungl.moduleexternalapi.member.service;

import com.juseungl.moduledomain.domains.member.domain.Member;
import com.juseungl.moduledomain.domains.member.dto.response.MemberInfoResponse;
import com.juseungl.moduledomain.domains.member.service.MemberCommandService;
import com.juseungl.moduledomain.domains.member.service.MemberQueryService;
import com.juseungl.moduleexternalapi.oauth.exception.AuthException;
import com.juseungl.moduleexternalapi.oauth.exception.JwtCustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.juseungl.moduleexternalapi.oauth.exception.JwtErrorCode.NOT_FOUND_MEMBER;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberQueryService memberQueryService;
    public MemberInfoResponse getMemberInfo(Long memberId) {
        Member member = memberQueryService.findById(memberId).orElseThrow(() -> new JwtCustomException(NOT_FOUND_MEMBER));
        return MemberInfoResponse.fromMember(member);
    }
}
