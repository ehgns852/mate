package com.bob.mate.global.util;

import com.bob.mate.domain.user.entity.User;
import com.bob.mate.domain.user.repository.UserRepository;
import org.springframework.stereotype.Component;

@Component
public class Util {
    private final UserRepository userRepository;
    private final IAuthenticationFacade authenticationFacade;

    public Util(UserRepository userRepository, IAuthenticationFacade authenticationFacade) {
        this.userRepository = userRepository;
        this.authenticationFacade = authenticationFacade;
    }

    public User findCurrentUser() {
        return userRepository.findById(1L).get(); // 테스트 용
        // 실제 사용시 : return userRepository.findByEmail(authenticationFacade.getPrincipalName());
    }


}
