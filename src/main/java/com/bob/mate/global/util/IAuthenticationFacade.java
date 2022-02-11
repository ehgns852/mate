package com.bob.mate.global.util;

import org.springframework.security.core.Authentication;

public interface IAuthenticationFacade {
    Authentication getAuthentication();
    String getPrincipalName();
}
