package com.bob.mate.global.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final PrincipalOauth2UserService principalOauth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .formLogin()
                .disable()
//                .authorizeRequests().antMatchers("/token/**").permitAll()
//                .and()
//                .oauth2Login().loginPage("/token/expired")
//                        .successHandler()
                .oauth2Login()
                .userInfoEndpoint()
                .userService(principalOauth2UserService)
                .and();
//                .successHandler();

//        http
//                .oauth2Login()
//                .userInfoEndpoint()
//                .userService(principalOauth2UserService);

    }


}
