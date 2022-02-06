package com.bob.mate.global.config.provider;

import com.bob.mate.domain.user.entity.Gender;

import java.util.Map;

public class KakaoUserInfo implements Oauth2UserInfo {


    private Map<String, Object> attributes;


    public KakaoUserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    @Override
    public String getProviderId() {
        return (String) attributes.get("sub");
    }

    @Override
    public String getProvider() {
        return "kakao";
    }

    @Override
    public String getEmail() {
        return (String) attributes.get("email");
    }

    @Override
    public String getNickName() {
        return (String) attributes.get("nickName");
    }

    @Override
    public Gender getGender() {
        if (attributes.get("gender").equals("male")) {
            return Gender.MAN;
        } else {
            return Gender.WOMAN;
        }
    }
}
