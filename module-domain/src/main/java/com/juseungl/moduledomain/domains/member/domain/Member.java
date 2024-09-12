package com.juseungl.moduledomain.domains.member.domain;

import com.juseungl.moduledomain.domains.auditing.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "member")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
//@SQLRestriction("deleted_at IS NULL")
//@SQLDelete(sql = "UPDATE user SET deleted_at = NOW() WHERE id = ?")
public class Member extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String provider;

    @Column(nullable = false, name = "social_id")
    private String socialId;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false, length = 255)
    private String email;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private Role role;

    @Column(name = "deleted_at") // 삭제 시각 저장
    private LocalDateTime deletedAt = null;

    @Builder
    public Member(String provider, String socialId, String nickname, String email, Role role) {
        this.nickname = nickname;
        this.provider = provider;
        this.socialId = socialId;
        this.email = email;
        this.role = role;
    }
}
