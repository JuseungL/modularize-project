package com.juseungl.moduleexternalapi.member.controller;

import com.juseungl.moduledomain.domains.member.domain.Member;
import com.juseungl.moduledomain.domains.member.service.MemberCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberCommandService memberCommandService;

//    @GetMapping("/test")
//    public String test() {
//        Member member = Member.builder()
//                .name("이주승")
//                .email("juseung0619@gmail.com")
//                .providerId("1234")
//                .provider("naver")
//                .build();
//
//        memberCommandService.createMember(member); // MemberService를 통해 회원 생성
//        return "hi";
//    }
}
