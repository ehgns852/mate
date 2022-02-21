package com.bob.mate.global.config;

import com.bob.mate.domain.user.service.AuthService;
import com.bob.mate.global.util.AuthenticationInterceptor;
import com.bob.mate.global.util.LoginMemberArgumentResolver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class WebMvcConfig implements WebMvcConfigurer {

    private final AuthService authService;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        log.info("in WebMvcConfig.addInterceptors");
        registry.addInterceptor(new AuthenticationInterceptor(authService))
                .addPathPatterns("/**");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        log.info("in WebMvcConfig.addArgumentResolvers");
        resolvers.add(new LoginMemberArgumentResolver(authService));
    }
}
