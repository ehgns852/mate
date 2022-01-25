package com.bob.mate.global.config;

import com.bob.mate.domain.user.entity.User;
import com.bob.mate.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PrincipalDetails implements UserDetailsService {

    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {

        User userEntity = userService.findByUserId(userId);

        userEntity.getRole();

        log.info("userEntity  = {}", userEntity);





    }
}
