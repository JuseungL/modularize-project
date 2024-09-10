package com.juseungl.moduledomain.domains.member.repository;

import com.juseungl.moduledomain.domains.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
