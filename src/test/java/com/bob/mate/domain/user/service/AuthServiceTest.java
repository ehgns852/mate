package com.bob.mate.domain.user.service;

import com.bob.mate.domain.user.dto.AccessTokenResponse;
import com.bob.mate.domain.user.dto.LoginMember;
import com.bob.mate.domain.user.dto.RefreshTokenRequest;
import com.bob.mate.domain.user.entity.Gender;
import com.bob.mate.global.config.redis.RedisUtil;
import com.bob.mate.global.exception.CustomException;
import com.bob.mate.global.jwt.JwtTokenProvider;
import com.bob.mate.global.jwt.Token;
import com.bob.mate.global.test.UserDummy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
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
    void findMemberByToken() {
        //given
        String accessToken = "accessToken";
        given(jwtTokenProvider.validateToken(accessToken)).willReturn(true);
        given(jwtTokenProvider.getPayload(accessToken)).willReturn("1");
        given(userService.findById(1L)).willReturn(UserDummy.createUserDummy(
                1L, "dohun", "hello@naver.com", Gender.MAN, "providerTest"));

        //when
        LoginMember memberByToken = authService.findMemberByToken(accessToken);

        //then
        assertThat(memberByToken.getId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("토큰이 만료 되었다면 리프레쉬 토큰을 레디스 서버에서 가져와서 새로운 엑세스 토큰을 만들어줌")
     void accessTokenByRefreshToken(){
        //given
        String accessToken = "accessToken";
        String refreshToken = "refreshToken";
        String newAccessToken = "newAccessToken";
        given(jwtTokenProvider.validateToken(anyString())).willReturn(true);
        given(jwtTokenProvider.getPayload(anyString())).willReturn("1");
        given(redisUtil.getData(anyString())).willReturn(refreshToken);
        given(jwtTokenProvider.createAccessToken(anyString())).willReturn(new Token(newAccessToken, 1L));

        //when
        AccessTokenResponse token = authService.accessTokenByRefreshToken(accessToken, new RefreshTokenRequest(refreshToken));

        //then
        assertThat(token.getAccessToken()).isEqualTo(newAccessToken);
    }





}
