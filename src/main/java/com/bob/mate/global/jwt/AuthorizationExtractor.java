package com.bob.mate.global.jwt;

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
    public static final String ACCESS_TOKEN_TYPE = AuthorizationExtractor.class.getSimpleName() + ".ACCESS_TOKEN_TYPE";

    public static String extract(HttpServletRequest request) {
        Enumeration<String> headers = request.getHeaders(AUTHORIZATION);
        log.info("headers = {}", headers);
        while (headers.hasMoreElements()) {
            String value = headers.nextElement();
            log.info("value = {}", value);
            if ((value.toLowerCase().startsWith(BEARER_TYPE.toLowerCase()))) {
                String authHeaderValue = value.substring(BEARER_TYPE.length()).trim();
                log.info("authHeaderValue = {}", authHeaderValue);
                request.setAttribute(ACCESS_TOKEN_TYPE, value.substring(0, BEARER_TYPE.length()).trim());
                int commaIndex = authHeaderValue.indexOf(',');
                if (commaIndex > 0) {
                    authHeaderValue = authHeaderValue.substring(0, commaIndex);
                    log.info("authHeaderValue = {}", authHeaderValue);
                }
                return authHeaderValue;
            }
        }
        return null;
    }
}

