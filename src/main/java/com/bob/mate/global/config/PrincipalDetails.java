package com.bob.mate.global.config;

import com.bob.mate.domain.user.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

//
//@Getter
//@NoArgsConstructor
//@Slf4j
//public class PrincipalDetails implements UserDetails{
//
//    private User user;
//
//    public PrincipalDetails(User userEntity) {
//        this.user = userEntity;
//    }
//
//
//
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        List<GrantedAuthority> roles = new ArrayList<>();
//        roles.add(() -> {
//            return String.valueOf(user.getRole());
//        });
//        return roles;
//    }
//
//    @Override
//    public String getPassword() {
//        return null;
//    }
//
//    @Override
//    public String getUsername() {
//        return null;
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return true;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return true;
//
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return true;
//    }
//
//}
