package com.juseungl.moduledomain.domains.member.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "member")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
//@SQLRestriction("deleted_at IS NULL")
//@SQLDelete(sql = "UPDATE user SET deleted_at = NOW() WHERE id = ?")
public class Member {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String provider;

    @Column(nullable = false, name = "provider_id")
    private String providerId;

    private String email;

    @Builder
    public Member(String name, String provider, String providerId, String email) {
        this.name = name;
        this.provider = provider;
        this.providerId = providerId;
        this.email = email;
    }
}
