package com.bob.mate.global.util;

import com.bob.mate.domain.user.service.AuthService;
import com.bob.mate.global.exception.CustomException;
import com.bob.mate.global.exception.ErrorCode;
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
        if (isPreflight(request) || isGet(request)) {
            log.info("preHandle true");
            validatesToken(request);
            return true;
        }
        log.info("after valitesToken");
        throw new CustomException(ErrorCode.UNAUTHORIZED_ACCESS_TOKEN);
    }


    protected void validatesToken(HttpServletRequest request) {
        String accessToken = AuthorizationExtractor.extract(request);
        log.info("accessToken = {}", accessToken);
        authService.validateAccessToken(accessToken);
    }

    protected boolean isPreflight(HttpServletRequest request) {
        return HttpMethod.OPTIONS.matches(request.getMethod());
    }

    protected boolean isGet(HttpServletRequest request) {
        return HttpMethod.GET.matches(request.getMethod());
    }

}
