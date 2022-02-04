package com.bob.mate.domain.user.controller;


import com.bob.mate.domain.user.dto.UserDto;
import com.bob.mate.domain.user.entity.User;
import com.bob.mate.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequestMapping("/user")
@RequiredArgsConstructor
@Slf4j
@RestController
public class UserController {
    private final UserService userService;

    @GetMapping("/{id}")
    public UserDto getUser(@PathVariable Long id){
        User findUser = userService.findById(id);
        UserDto userDto = new UserDto(findUser);
        return userDto;
    }



}
