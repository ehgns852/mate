package com.bob.mate.global.util;

import com.bob.mate.domain.user.service.AuthService;
import com.bob.mate.global.jwt.AuthorizationExtractor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
public class AuthenticationInterceptor implements HandlerInterceptor {

    private final AuthService authService;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (isPreflight(request) || isGet(request)) {
            return true;
        }

        validatesToken(request);
        return true;
    }


    protected void validatesToken(HttpServletRequest request) {
        String accessToken = AuthorizationExtractor.extract(request);
        authService.validateAccessToken(accessToken);
    }

    protected boolean isPreflight(HttpServletRequest request) {
        return HttpMethod.OPTIONS.matches(request.getMethod());
    }

    protected boolean isGet(HttpServletRequest request) {
        return HttpMethod.GET.matches(request.getMethod());
    }

}
