package com.bob.mate;

import com.bob.mate.domain.user.entity.User;
import com.bob.mate.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

import static com.bob.mate.domain.user.entity.Role.ADMIN;
import static com.bob.mate.domain.user.entity.Role.USER;

@Component
@RequiredArgsConstructor
@Slf4j
public class InitTest {

    private final UserService userService;

//    @PostConstruct
//    @Transactional
//    public void dataInit(){
//
//    }
}
