package com.bob.mate.domain.user.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@RequestMapping("/user")
@RequiredArgsConstructor
@Slf4j
@Controller
public class UserController {



    @GetMapping("/login")
    public String userLogin(Model model) {
        log.info("userController.Get in");
        return "user/login";
    }
}
