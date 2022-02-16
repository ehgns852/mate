package com.bob.mate.global.util;

import com.bob.mate.domain.user.entity.User;
import com.bob.mate.domain.user.repository.UserRepository;

public class Util {
    private final UserRepository userRepository;
    private final IAuthenticationFacade authenticationFacade;

    public Util(UserRepository userRepository, IAuthenticationFacade authenticationFacade) {
        this.userRepository = userRepository;
        this.authenticationFacade = authenticationFacade;
    }

    public User findCurrentUser() {
        return userRepository.findByEmail(authenticationFacade.getPrincipalName());
    }


}
