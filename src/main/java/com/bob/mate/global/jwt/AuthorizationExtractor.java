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
        throw new CustomException(ErrorCode.UNAUTHORIZED_ACCESS_TOKEN);
    }
}

