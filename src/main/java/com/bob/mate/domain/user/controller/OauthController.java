package com.bob.mate.domain.user.controller;

import com.bob.mate.domain.user.dto.AccessTokenResponse;
import com.bob.mate.domain.user.dto.AuthorizationRequest;
import com.bob.mate.domain.user.dto.LoginResponse;
import com.bob.mate.domain.user.dto.RefreshTokenRequest;
import com.bob.mate.domain.user.service.AuthService;
import com.bob.mate.domain.user.service.OauthService;
import com.bob.mate.global.dto.CustomResponse;
import com.bob.mate.global.exception.CustomException;
import com.bob.mate.global.exception.ErrorCode;
import com.bob.mate.global.jwt.AuthorizationExtractor;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
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
    @Operation(summary = "OAuth 로그인 API", description = "코드를 받아와서 로그인 처리를 하는 API")
    @ApiResponse(responseCode = "200", description = "정상적으로 로그인 처리가 된 경우")
    @GetMapping("/login/oauth/{provider}")
    public LoginResponse login(@PathVariable String provider, @RequestParam String code) {
        log.info("In OauthController");
        return oauthService.login(new AuthorizationRequest(provider, code));

    }

    /**
     * access token 갱신
     */
    @Operation(summary = "엑세스 토큰 갱신 API", description = "리프레쉬 토큰으로 엑세스 토큰을 갱신하는 API")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "엑세스 토큰을 정상적으로 발급 받은 경우"),
            @ApiResponse(responseCode = "400", description = "Form 입력값이 잘못된 경우"),
            @ApiResponse(responseCode = "401", description = "토큰 검증이 실패한 경우")
    })
    @PostMapping(value = "/token", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public AccessTokenResponse updateAccessToken(HttpServletRequest request,
                                                 @Validated RefreshTokenRequest refreshToken,
                                                 BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new CustomException(ErrorCode.BAD_REQUEST_Token);
        }
        String accessToken = AuthorizationExtractor.extract(request);
        log.info("accessToken = {}", accessToken);
        return authService.accessTokenByRefreshToken(accessToken, refreshToken);
    }

    @Operation(summary = "로그아웃 API", description = "엑세스 토큰을 받아와서 로그아웃을 처리하는 API")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "유저가 정상적으로 로그아웃이 된 경우"),
            @ApiResponse(responseCode = "401", description = "토큰 검증이 실패한 경우")
    })
    @PostMapping("/logout/me")
    public CustomResponse logout(HttpServletRequest request) {
        String accessToken = AuthorizationExtractor.extract(request);
        return authService.logout(accessToken);
    }
}
