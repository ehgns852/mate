package com.bob.mate.global.config;

import com.bob.mate.domain.user.entity.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Slf4j
@Getter
@RequiredArgsConstructor
public class PrincipalOauth2Details implements OAuth2User {

    private final User user;
    private final Map<String,Object> attributes;

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
        log.info("roles = {}", roles);
        return roles;
    }

    @Override
    public String getName() {
        log.info("user.getName = {}", user.getUserProfile().getNickName());
        return user.getUserProfile().getNickName();
    }
}
