package com.bob.mate.global.config.provider;

import com.bob.mate.domain.user.entity.Gender;

public interface Oauth2UserInfo {
    String getProviderId();
    String getProvider();
    String getEmail();
    String getNickName();
    Gender getGender();
    String getImageUrl();
}
