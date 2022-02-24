package com.bob.mate.domain.user.controller;

import com.bob.mate.domain.user.dto.AccessTokenResponse;
import com.bob.mate.domain.user.dto.AuthorizationRequest;
import com.bob.mate.domain.user.dto.LoginResponse;
import com.bob.mate.domain.user.dto.RefreshTokenRequest;
import com.bob.mate.domain.user.service.AuthService;
import com.bob.mate.domain.user.service.OauthService;
import com.bob.mate.global.jwt.AuthorizationExtractor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@Slf4j
@RestController
public class OauthController {

    private final OauthService oauthService;
    private final AuthService authService;

    /**
     * OAuth 로그인 시 인증 코드를 넘겨받은 후 첫 로그인 시 회원가입
     */
    @GetMapping("/login/oauth/{provider}")
    public LoginResponse login(@PathVariable String provider, @RequestParam String code) {
        log.info("In OauthController");
        return oauthService.login(new AuthorizationRequest(provider, code));
    }

    /**
     * access token 갱신
     */
    @PostMapping(value = "/token", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public AccessTokenResponse updateAccessToken(HttpServletRequest request,
                                                 RefreshTokenRequest refreshToken) {
        String accessToken = AuthorizationExtractor.extract(request);
        log.info("accessToken = {}", accessToken);
        return authService.accessTokenByRefreshToken(accessToken, refreshToken);
    }


    @PostMapping("/logout")
    public void logout(HttpServletRequest request) {
        String accessToken = AuthorizationExtractor.extract(request);
        authService.logout(accessToken);
    }



}
