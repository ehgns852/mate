package com.bob.mate.domain.user.controller;


import com.bob.mate.domain.user.dto.UserDto;
import com.bob.mate.domain.user.dto.UserProfileRequest;
import com.bob.mate.domain.user.dto.UserRequest;
import com.bob.mate.domain.user.entity.User;
import com.bob.mate.domain.user.service.UserService;
import com.bob.mate.global.dto.CustomResponse;
import com.bob.mate.global.exception.CustomException;
import com.bob.mate.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;


@RequestMapping("/user")
@RequiredArgsConstructor
@Slf4j
@RestController
public class UserController {
    private final UserService userService;

    @GetMapping("/{id}")
    public UserDto getUser(@PathVariable Long id) {
        User findUser = userService.findById(id);
        return new UserDto(findUser);
    }

    @PatchMapping("{userId}/nickname")
    public CustomResponse updateNickname(@PathVariable Long userId,
                                         @Valid @RequestBody UserRequest userRequest,
                                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new CustomException(ErrorCode.BAD_REQUEST_NICKNAME);
        }
        return userService.createNickName(userId, userRequest);
    }


    @DeleteMapping("/delete/{id}")
    public CustomResponse deleteUser(@PathVariable Long id) {
        return userService.deleteUser(id);
    }


    @PostMapping("{userId}/profile")
    public CustomResponse updateProfile(@PathVariable Long userId,
                                        @RequestParam(value = "profile",required = false) MultipartFile multipartFile,
                                        @Valid @RequestBody UserProfileRequest userProfileRequest,
                                        BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new CustomException(ErrorCode.BAD_REQUEST_PROFILE);
        }
        return userService.updateProfile(userId, multipartFile, userProfileRequest);
    }
}