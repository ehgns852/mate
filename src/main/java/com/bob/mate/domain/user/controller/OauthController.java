package com.bob.mate.domain.user.controller;

import com.bob.mate.domain.user.dto.AuthorizationRequest;
import com.bob.mate.domain.user.dto.LoginResponse;
import com.bob.mate.domain.user.service.OauthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Slf4j
@RestController
public class OauthController {

    private final OauthService oauthService;

    /**
     * OAuth 로그인 시 인증 코드를 넘겨받은 후 첫 로그인 시 회원가입
     */
    @GetMapping("/login/oauth/{provider}")
    public ResponseEntity<LoginResponse> login(@PathVariable String provider, @RequestParam String code) {
        log.info("In OauthController");
        LoginResponse loginResponse = oauthService.login(new AuthorizationRequest(provider,code));
        log.info("loginResponse = {}", loginResponse.getAccessToken());

        return ResponseEntity.ok().body(loginResponse);
    }
}
