package com.bob.mate.global.config.provider;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;


class KakaoUserInfoTest {

    private Map<String, Object> user = new HashMap<>();


    @Test
    public void test() {
        //given
        user.put("email", null);
        //when
        user.get("email");
        System.out.println("user = " + user);
        //then

    }

}