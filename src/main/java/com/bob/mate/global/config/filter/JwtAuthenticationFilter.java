package com.bob.mate.global.config.filter;

import com.bob.mate.domain.user.dto.LoginMember;
import com.bob.mate.domain.user.service.AuthService;
import com.bob.mate.global.jwt.AuthorizationExtractor;
import com.bob.mate.global.util.UserAuthentication;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final AuthService authService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (request.getMethod().equals("OPTIONS")) {
            logger.debug("request options method is options.");
        }
        log.info("in JwtAuthenticationFilter");
        String accessToken = AuthorizationExtractor.extract(request);

        log.info("accessToken = {}", accessToken);

        LoginMember loginMember = authService.findMemberByToken(accessToken);
        log.info("loginMember = {}", loginMember.getId());

        UserAuthentication authentication = new UserAuthentication(loginMember.getId());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        log.info("Authenticaiton = {}", authentication.getUserId());

        chain.doFilter(request,response);
    }
}
