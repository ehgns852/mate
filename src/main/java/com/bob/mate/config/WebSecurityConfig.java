package com.bob.mate.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {



    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();

        http
                .authorizeRequests()
//                .antMatchers("/admin/**").hasRole("ADMIN")
//                .and()
//                .authorizeRequests()
//                .antMatchers("/", "/account/register", "/css/**", "/api/**").permitAll()
                .anyRequest().permitAll();
//                .and()
//                .formLogin()
//                .loginPage("/user/login")
//                .loginProcessingUrl("/user/login") //login 주소가 호출이 되면 시큐리티가 낚아채 준다.
//                .defaultSuccessUrl("/")
//                .permitAll()
//                .and()
//                .logout()
//                .permitAll();
//        http
//                .oauth2Login()
//                .loginPage("/user/login")
//                .userInfoEndpoint();


//    }
//
//    @Bean
//    @Override
//    public AuthenticationManager authenticationManagerBean() throws Exception {
//        return super.authenticationManagerBean();
    }


}
