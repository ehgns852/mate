package com.bob.mate.domain.user.controller;

import com.bob.mate.domain.user.dto.AuthorizationRequest;
import com.bob.mate.domain.user.dto.LoginResponse;
import com.bob.mate.domain.user.service.OauthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@Slf4j
@RestController
public class OauthController {

    private final OauthService oauthService;

    /**
     * OAuth 로그인 시 인증 코드를 넘겨받은 후 첫 로그인 시 회원가입
     */
    @GetMapping("/login/oauth/{provider}")
    public ResponseEntity<LoginResponse> login(AuthorizationRequest authorizationRequest) {
        log.info("In OauthController");
        log.info("code = {}", authorizationRequest.getCode());
        LoginResponse loginResponse = oauthService.login(authorizationRequest);
        log.info("loginResponse = {}", loginResponse.getAccessToken());

        return ResponseEntity.ok().body(loginResponse);
    }
}
