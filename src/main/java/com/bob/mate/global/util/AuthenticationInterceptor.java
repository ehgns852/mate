package com.bob.mate.global.util;

import com.bob.mate.domain.user.service.AuthService;
import com.bob.mate.global.jwt.AuthorizationExtractor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
@Slf4j
public class AuthenticationInterceptor implements HandlerInterceptor {

    private final AuthService authService;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("in preHandle");
        if (isPreflight(request)) {
            log.info("preHandle true");
            log.info("request.getMethod = {}", request.getMethod());
            return true;
        }
        validatesToken(request);
        log.info("after valitesToken");
        log.info("request.getMethod = {}", request.getMethod());
        return true;
    }

    protected void validatesToken(HttpServletRequest request) {
        String accessToken = AuthorizationExtractor.extract(request);
        log.info("accessToken = {}", accessToken);
        authService.validateAccessToken(accessToken);
    }

    protected boolean isPreflight(HttpServletRequest request) {
        return HttpMethod.OPTIONS.matches(request.getMethod());
    }

}
