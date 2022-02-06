package com.bob.mate.global.config;

import com.bob.mate.domain.user.entity.Gender;
import com.bob.mate.domain.user.entity.User;
import com.bob.mate.domain.user.repository.UserRepository;
import com.bob.mate.global.config.provider.KakaoUserInfo;
import com.bob.mate.global.config.provider.Oauth2UserInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;


@Service
@Slf4j
@RequiredArgsConstructor
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        log.info("ClientRegistration = {}", userRequest.getClientRegistration());
        log.info("AccessToken = {}", userRequest.getAccessToken().getTokenValue());
        /**
         * 구글 로그인 버튼 클릭 -> 구글로그인창 -> 로그인 완료 -> code를 리턴(OAuth-client 라이브러리) ->AccessToken 요청
         * userRequest 정보 -> 회원프로필 받아야함(loadUser 함수) -> 구글로부터 회원 프로필을 받아준다.
         */

        OAuth2User oAuth2User = super.loadUser(userRequest);
        log.info("Attributes = {}", oAuth2User.getAttributes());

        Oauth2UserInfo oauth2UserInfo = null;
        if (userRequest.getClientRegistration().getRegistrationId().equals("kakao")) {
            log.info("카카오 로그인 요청");
            oauth2UserInfo = new KakaoUserInfo(oAuth2User.getAttributes());
        } else {
            log.info("허용되지 않은 접근 입니다.");
        }


        String provider = oauth2UserInfo.getProvider();
        String providerId = oauth2UserInfo.getProviderId();
        String nickName = oauth2UserInfo.getNickName();
        String email = oauth2UserInfo.getEmail();
        Gender gender = oauth2UserInfo.getGender();

        User userEntity = userRepository.findByEmail(email);

        log.info("userEmail = {}", userEntity);
        if (userEntity == null) {
            userEntity = User.createUser(email, nickName, gender, provider, providerId);
            userRepository.save(userEntity);

            log.info("userEntity = {}", userEntity);

            return new PrincipalOauth2Details(userEntity, oAuth2User.getAttributes());
        } else {
            return new PrincipalOauth2Details(userEntity, oAuth2User.getAttributes());
        }

    }
}

