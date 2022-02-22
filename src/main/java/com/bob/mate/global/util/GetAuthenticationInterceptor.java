package com.bob.mate.global.util;

import com.bob.mate.domain.user.service.AuthService;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class GetAuthenticationInterceptor extends AuthenticationInterceptor {

    public GetAuthenticationInterceptor(AuthService authService) {
        super(authService);
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (isPreflight(request) || !isGet(request)) {
            log.info("GetAuthentication preHandle TRUE");
            return true;
        }
        validatesToken(request);
        return true;
    }
}
