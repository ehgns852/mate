package com.bob.mate.global.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final PrincipalOauth2UserService principalOauth2UserService;
    private final CorsConfig corsConfig;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .formLogin()
                .disable()
                .cors().configurationSource(corsConfig.corsConfigurationSource())
                .and()
//                .authorizeRequests().antMatchers("/token/**").permitAll()
//                .and()
//                .oauth2Login().loginPage("/token/expired")
//                        .successHandler()
                .oauth2Login()
                .userInfoEndpoint()
                .userService(principalOauth2UserService);
//                .successHandler();

//        http
//                .oauth2Login()
//                .userInfoEndpoint()
//                .userService(principalOauth2UserService);

    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(HttpMethod.GET, "/user/**")
                .antMatchers(HttpMethod.GET, "/login/oauth/**")
                .antMatchers(HttpMethod.GET, "/posts")
                .antMatchers("/")
                .antMatchers("/static/**")
                .antMatchers("/favicon.ico", "/manifest.json", "/logo*.png");
    }
}
