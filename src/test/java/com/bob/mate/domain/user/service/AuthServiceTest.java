package com.bob.mate.domain.user.service;

import com.bob.mate.domain.user.dto.LoginMember;
import com.bob.mate.domain.user.entity.Gender;
import com.bob.mate.global.config.redis.RedisUtil;
import com.bob.mate.global.exception.CustomException;
import com.bob.mate.global.jwt.JwtTokenProvider;
import com.bob.mate.global.test.UserDummy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {


    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @Mock
    private UserService userService;

    @Mock
    private RedisUtil redisUtil;

    @InjectMocks
    private AuthService authService;


    @Test
    @DisplayName("access token이 유효한지 검증")
    void validateAccessToken() {

        //given
        String accessToken = "accessToken";
        //when
        given(jwtTokenProvider.validateToken(accessToken)).willReturn(false);
        //then
        assertThatThrownBy(() -> authService.validateAccessToken(accessToken))
                .isInstanceOf(CustomException.class);

    }

    @Test
    @DisplayName("유효한 토큰으로 User 찾기")
    public void findMemberByToken() {
        //given
        String accessToken = "accessToken";
        given(jwtTokenProvider.validateToken(accessToken)).willReturn(true);
        given(jwtTokenProvider.getPayload(accessToken)).willReturn("1");
        given(userService.findById(1L)).willReturn(UserDummy.createUserDummy(
                1L, "dobi", "dsasjd@naver.com", Gender.MAN, "123123"));


        //when
        LoginMember memberByToken = authService.findMemberByToken(accessToken);

        //then
        assertThat(memberByToken.getId()).isEqualTo(1L);

    }
}
