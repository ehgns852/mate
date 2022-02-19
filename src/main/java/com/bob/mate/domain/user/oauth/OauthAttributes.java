package com.bob.mate.domain.user.oauth;

import com.bob.mate.domain.user.entity.User;
import com.bob.mate.domain.user.entity.UserProfile;
import com.bob.mate.global.config.provider.KakaoUserInfo;

import java.util.Arrays;
import java.util.Map;

public enum OauthAttributes {
    KAKAO("kakao") {
        @Override
        public User of(Map<String, Object> attributes) {
            KakaoUserInfo kakaoUserInfo = new KakaoUserInfo(attributes);
            return User.createUser(
                    kakaoUserInfo.getEmail(),
                    kakaoUserInfo.getNickName(),
                    kakaoUserInfo.getGender(),
                    kakaoUserInfo.getProvider(),
                    kakaoUserInfo.getProviderId(),
                    kakaoUserInfo.getImageUrl()
            );
        }
    };

        private final String providerName;

    OauthAttributes(String providerName) {
        this.providerName = providerName;
    }

    public static User extract(String providerName, Map<String, Object> userAttributes) {
        return Arrays.stream(values())
                .filter(provider -> providerName.equals(provider.providerName))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new)
                .of(userAttributes);
    }

    public abstract User of(Map<String, Object> attributes);
}
