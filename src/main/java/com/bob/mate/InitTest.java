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

    @PostConstruct
    @Transactional
    public void dataInit(){
        User user1 = User.builder().username("123")
                .password("123")
                .role(USER)
                .build();
        User user2 = User.builder().username("321")
                .password("321")
                .role(USER)
                .build();

        userService.save(user1);
        userService.save(user2);

    }
}
