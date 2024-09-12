package com.juseungl.moduleexternalapi.oauth.dto;

import com.juseungl.moduledomain.domains.member.domain.Member;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;

import java.util.Collection;
import java.util.Map;

public class OAuth2UserImpl extends DefaultOAuth2User implements UserDetails {
    private final Member member;
    public OAuth2UserImpl(Collection<? extends GrantedAuthority> authorities,
                          Map<String, Object> attributes,
                          String nameAttributeKey,
                          Member member) {
        super(authorities, attributes, nameAttributeKey);
        this.member = member;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return member.getId().toString();
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    public Member getMember() {
        return member;
    }
}
