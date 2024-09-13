package com.juseungl.moduleexternalapi.member.controller;

import com.juseungl.moduledomain.domains.member.domain.Member;
import com.juseungl.moduledomain.domains.member.service.MemberCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberCommandService memberCommandService;

    @GetMapping("/test")
    public String test(Principal principal) {
        String name = principal.getName();
        System.out.println(name);

//        memberCommandService.createMember(member); // MemberService를 통해 회원 생성
        return "hi";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/admin")
    public String adminAccess() {
        return "This is an admin-only resource.";
    }
}
