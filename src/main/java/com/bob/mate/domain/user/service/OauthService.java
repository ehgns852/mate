package com.bob.mate.domain.user.service;

import com.bob.mate.domain.user.dto.LoginResponse;
import com.bob.mate.domain.user.dto.OauthTokenResponse;
import com.bob.mate.domain.user.entity.Gender;
import com.bob.mate.domain.user.entity.User;
import com.bob.mate.domain.user.repository.UserRepository;
import com.bob.mate.global.config.provider.KakaoUserInfo;
import com.bob.mate.global.config.provider.Oauth2UserInfo;
import com.bob.mate.global.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class OauthService {
    private static final String BEARER_TYPE = "Bearer";

    private final InMemoryClientRegistrationRepository inMemoryRepository;
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;


    /**
     * @InMemoryRepository application-oauth properties 정보를 담고 있음
     * @getToken() 넘겨받은 code 로 Oauth 서버에 Token 요청
     * @getUserProfile 첫 로그인 시 회원가입
     * 유저 인증 후 Jwt AccessToken, Refresh Token 생성
     * TODO REDIS 에 Refresh Token 저장
     */
    @Transactional
    public LoginResponse login(String providerName, String code) {

        log.info("in OauthService");
        ClientRegistration provider = inMemoryRepository.findByRegistrationId(providerName);
        log.info("provider.getClientId = {}", provider.getClientId());
        OauthTokenResponse tokenResponse = getToken(code, provider);
        log.info("tokenResponse = {}", tokenResponse.getAccessToken());
        User user = getUserProfile(providerName,tokenResponse,provider);
        log.info("user = {}", user);

        String accessToken = jwtTokenProvider.createAccessToken(String.valueOf(user.getId()));
        log.info("accessToken = {}", accessToken);
        String refreshToken = jwtTokenProvider.createRefreshToken();
        log.info("refreshToken = {}", refreshToken);

        return LoginResponse.builder()
                .id(user.getId())
                .name(user.getUserProfile().getNickName())
                .email(user.getEmail())
                .imageUrl(user.getUserProfile().getImageUrl())
                .role(user.getRole())
                .tokenType(BEARER_TYPE)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    private OauthTokenResponse getToken(String code, ClientRegistration provider) {
        log.info("OauthService.getToken In");
        log.info("provider.TokenUri = {}" , provider.getProviderDetails().getTokenUri());
        return WebClient.create()
                .post()
                .uri(provider.getProviderDetails().getTokenUri())
                .headers(header -> {
                    header.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
                    header.setAcceptCharset(Collections.singletonList(StandardCharsets.UTF_8));
                    log.info("header = {}", header);
                })
                .bodyValue(tokenRequest(code, provider))
                .retrieve()
                .bodyToMono(OauthTokenResponse.class)
                .block();

    }


    private MultiValueMap<String, String> tokenRequest(String code, ClientRegistration provider) {
        log.info("tokenRequest In");
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("code", code);
        formData.add("grant_type", "authorization_code");
        formData.add("redirect_uri", provider.getRedirectUri());
        formData.add("client_secret",provider.getClientSecret());
        formData.add("client_id",provider.getClientId());
        log.info("redirectUri = {}", provider.getRedirectUri());
        return formData;
    }

    private User getUserProfile(String providerName, OauthTokenResponse tokenResponse, ClientRegistration provider) {
        Map<String, Object> userAttributes = getUserAttributes(provider, tokenResponse);
        log.info("userAttributes = {}", userAttributes);
        Oauth2UserInfo oauth2UserInfo = null;
        if (providerName.equals("kakao")) {
            log.info("카카오 로그인 요청");
            oauth2UserInfo = new KakaoUserInfo(userAttributes);
            log.info("oauth2USer = {}", oauth2UserInfo);
        } else {
            log.info("허용되지 않은 접근 입니다.");
        }

        String provide = oauth2UserInfo.getProvider();
        String providerId = oauth2UserInfo.getProviderId();
        String nickName = oauth2UserInfo.getNickName();
        String email = oauth2UserInfo.getEmail();
        Gender gender = oauth2UserInfo.getGender();
        String imageUrl = oauth2UserInfo.getImageUrl();

        User userEntity = userRepository.findByEmail(email);

        log.info("userEmail = {}", userEntity);
        if (userEntity == null) {
            userEntity = User.createUser(email, nickName, gender, provide, providerId,imageUrl);
            userRepository.save(userEntity);

            log.info("userEntity = {}", userEntity);
        }
        return userEntity;
    }

    private Map<String, Object> getUserAttributes(ClientRegistration provider, OauthTokenResponse tokenResponse) {
        log.info("getUserAttributes In");
        log.info("userinfoUri = {}", provider.getProviderDetails().getUserInfoEndpoint().getUri());
        return WebClient.create()
                .get()
                .uri(provider.getProviderDetails().getUserInfoEndpoint().getUri())
                .headers(header -> header.setBearerAuth(tokenResponse.getAccessToken()))
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
                .block();
    }

}
