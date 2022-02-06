package com.bob.mate.global.config;

import com.bob.mate.domain.user.entity.User;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Slf4j
@Getter
public class PrincipalOauth2Details implements OAuth2User {

    private User user;
    private Map<String,Object> attributes;

    public PrincipalOauth2Details(User user, Map<String, Object> attributes) {
        this.user = user;
        this.attributes = attributes;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> roles = new ArrayList<>();
        roles.add(() -> {
            return String.valueOf(user.getRole());
        });
        return roles;
    }

    @Override
    public String getName() {
        return user.getUserProfile().getNickName();
    }
}
