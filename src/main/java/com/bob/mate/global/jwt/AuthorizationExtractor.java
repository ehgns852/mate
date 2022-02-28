package com.bob.mate.global.jwt;

import com.bob.mate.global.exception.CustomException;
import com.bob.mate.global.exception.ErrorCode;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;

import java.util.Enumeration;

import static lombok.AccessLevel.*;

@NoArgsConstructor(access = PRIVATE)
@Slf4j
public class AuthorizationExtractor {

    public static final String AUTHORIZATION = "Authorization";
    public static String BEARER_TYPE = "Bearer";

    public static String extract(HttpServletRequest request) {
        Enumeration<String> headers = request.getHeaders(AUTHORIZATION);
        log.info("headers = {}", headers);
        while (headers.hasMoreElements()) {
            String value = headers.nextElement();
            log.info("value = {}", value);
            if ((value.toLowerCase().startsWith(BEARER_TYPE.toLowerCase()))) {
                String authHeaderValue = value.substring(BEARER_TYPE.length()).trim();
                log.info("authHeaderValue = {}", authHeaderValue);
                return authHeaderValue;
            }
        }
        // CustomException 클래스는 서비스 로직단에서만 먹히는 에러 클래스입니다.
        // 필터 단에서 발생하는 예외 메시지를 정상적으로 보내기 위해서는
        // AuthenticationEntryPoint (인증 관련) 이나
        // AccessDeniedHandler (인가 관련) 을 사용해야 하므로 다른 방식을 쓰셔야 합니다.
        throw new CustomException(ErrorCode.UNAUTHORIZED_ACCESS_TOKEN);
    }
}

