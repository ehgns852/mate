package com.bob.mate.global.config.filter;

import com.bob.mate.domain.user.dto.LoginMember;
import com.bob.mate.domain.user.service.AuthService;
import com.bob.mate.global.exception.CustomException;
import com.bob.mate.global.jwt.AuthorizationExtractor;
import com.bob.mate.global.jwt.JwtTokenProvider;
import com.bob.mate.global.util.UserAuthentication;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final AuthService authService;
    private final JwtTokenProvider jwtTokenProvider;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.info("in JwtAuthenticationFilter");
        if (request.getMethod().equals("OPTIONS")) {
            chain.doFilter(request,response);
            return;
        }
        try {
            String accessToken = AuthorizationExtractor.extract(request);
            log.info("accessToken = {}", accessToken);
            if (StringUtils.hasText(accessToken) && jwtTokenProvider.validateToken(accessToken)) {
                LoginMember loginMember = authService.findMemberByToken(accessToken);
                log.info("loginMember = {}", loginMember.getId());
                UserAuthentication authentication = new UserAuthentication(loginMember.getId());
                SecurityContextHolder.getContext().setAuthentication(authentication);
                log.info("Authentication = {}", authentication.getUserId());
            }
        } catch (CustomException e) {
            log.info("JwtAuthentication CustomException");
            request.setAttribute("CustomException", e);
        }
            chain.doFilter(request,response);
        }

}
