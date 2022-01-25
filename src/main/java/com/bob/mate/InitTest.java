package com.bob.mate;

import com.bob.mate.domain.user.entity.User;
import com.bob.mate.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import static com.bob.mate.domain.user.entity.Role.ADMIN;
import static com.bob.mate.domain.user.entity.Role.USER;

@Component
@RequiredArgsConstructor
@Slf4j
public class InitTest {

    private final UserService userService;

    @PostConstruct
    public void dataInit(){
        User user1 = new User("123","123", ADMIN);
        User user2 = new User("321","321", USER);

        userService.saveUser(user1);
        userService.saveUser(user2);

    }
}
