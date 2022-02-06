package com.bob.mate.global.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final PrincipalOauth2UserService principalOauth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();

//        http
//                .authorizeRequests()
//                .anyRequest().permitAll()
//                .and()
//                .formLogin()
//                .loginPage("/user/login")
//                .loginProcessingUrl("/user/login") //login 주소가 호출이 되면 시큐리티가 낚아채 준다.
//                .defaultSuccessUrl("/")
//                .permitAll()
//                .and()
//                .logout()
//                .permitAll();
        http
                .oauth2Login()
                .userInfoEndpoint()
                .userService(principalOauth2UserService);

//    }
//
//    @Bean
//    @Override
//    public AuthenticationManager authenticationManagerBean() throws Exception {
//        return super.authenticationManagerBean();
    }


}
