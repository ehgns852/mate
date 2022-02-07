package com.bob.mate.global.config.provider;

import com.bob.mate.domain.user.entity.Gender;

import java.util.Map;

public class KakaoUserInfo implements Oauth2UserInfo {


    private Map<String, Object> attributes;


    public Map<String, Object> getKakaoAccount(){
        return(Map<String, Object>) attributes.get("kakao_account");
    }

    public Map<String, Object> getProfile(){
        Map<String, Object> kakaoAccount = getKakaoAccount();
        return (Map<String, Object>) kakaoAccount.get("profile");
    }


    public KakaoUserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    @Override
    public String getProviderId() {
        return String.valueOf(attributes.get("id"));
    }

    @Override
    public String getProvider() {
        return "kakao";
    }

    @Override
    public String getEmail() {
        Map<String, Object> kakaoAccount = getKakaoAccount();
        return (String) kakaoAccount.get("email");
    }

    @Override
    public String getNickName() {
        Map<String, Object> profile = getProfile();
        return (String) profile.get("nickname");
    }

    @Override
    public Gender getGender() {
        Map<String, Object> kakaoAccount = getKakaoAccount();
        String gender = (String) kakaoAccount.get("gender");

        if (gender.equals("male")) {
            return Gender.MAN;
        } else {
            return Gender.WOMAN;
        }
    }
}
