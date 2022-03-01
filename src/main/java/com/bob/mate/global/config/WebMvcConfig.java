package com.bob.mate.global.config;

import com.bob.mate.domain.user.service.AuthService;
import com.bob.mate.global.util.AuthenticationInterceptor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class WebMvcConfig implements WebMvcConfigurer {

    private final AuthService authService;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedMethods("GET", "POST", "DELETE", "PATCH", "OPTIONS")
                .allowedHeaders("*")
                .allowedOrigins("*")
                .exposedHeaders("*");

        WebMvcConfigurer.super.addCorsMappings(registry);
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        log.info("in WebMvcConfig.addInterceptors");

        List<String> addPathPattern = List.of(
                "/user/**"
        );

        List<String> excludePathPattern = List.of(
                "/user/image/**"
        );

        registry.addInterceptor(new AuthenticationInterceptor(authService))
                .addPathPatterns(addPathPattern)
                .excludePathPatterns(excludePathPattern);
    }

}
