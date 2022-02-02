package com.bob.mate;

import com.bob.mate.domain.user.entity.Address;
import com.bob.mate.domain.user.entity.Gender;
import com.bob.mate.domain.user.entity.User;
import com.bob.mate.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
@Slf4j
public class InitTest {

    private final UserService userService;

    @PostConstruct
    @Transactional
    public void dataInit(){
        Address address = new Address("seoul", "songpa", "dsajdlkas");

        User dohun = User.createUser("dsajklda@naver.com", "dohun", address, "123123231"
                , Gender.MAN, 12);

        userService.save(dohun);
    }
}
