package com.bob.mate.domain.user.controller;


import com.bob.mate.domain.user.dto.UserProfileQueryDto;
import com.bob.mate.domain.user.dto.UserProfileRequest;
import com.bob.mate.domain.user.dto.UserProfileResponse;
import com.bob.mate.domain.user.dto.UserResponse;
import com.bob.mate.domain.user.entity.User;
import com.bob.mate.domain.user.service.UserService;
import com.bob.mate.global.dto.CustomResponse;
import com.bob.mate.global.exception.CustomException;
import com.bob.mate.global.exception.ErrorCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
@RestController
public class UserController {
    private final UserService userService;

    @Operation(summary = "유저 단건 조회 API", description = "유저 ID를 받아와서 유저 정보를 반환하는 API")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "유저 정보가 정상 리턴된 경우"),
            @ApiResponse(responseCode = "404", description = "입력받은 userId로 찾을 수 없는 경우")
    })
    @GetMapping("/{id}")
    public UserResponse getUser(@PathVariable Long id) {
        User findUser = userService.findById(id);
        return new UserResponse(findUser);
    }


    @Operation(summary = "유저 정보 삭제 API", description = "유저 ID를 받아와서 삭제하는 API")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "회원 탈퇴가 정상적으로 성공된 경우"),
            @ApiResponse(responseCode = "404", description = "입력받은 회원 ID를 찾지 못한경우")
    })
    @DeleteMapping("/{id}")
    public CustomResponse deleteUser(@PathVariable Long id) {
        return userService.deleteUser(id);
    }


    @Operation(summary = "유저 프로필 생성 및 수정 API", description = "유저 ID를 받아와서 프로필을 생성 및 수정 하는 API")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "회원 프로필이 정상적으로 저장된 경우"),
            @ApiResponse(responseCode = "400", description = "Request Body 입력값이 잘못된 경우"),
            @ApiResponse(responseCode = "404", description = "받아온 ID로 유저를 찾지 못한 경우")
    })
    @PostMapping(value = "/{userId}/profile", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public UserProfileResponse updateProfile(@PathVariable Long userId,
                                             @RequestPart(value = "file", required = false) MultipartFile multipartFile,
                                             @Validated @RequestPart(value = "json") UserProfileRequest userProfile,
                                             BindingResult bindingResult) throws IOException {
        if (bindingResult.hasErrors()) {
            throw new CustomException(ErrorCode.BAD_REQUEST_PROFILE);
        }
        return userService.updateProfile(userId, multipartFile, userProfile);
    }


    @Operation(summary = "유저 프로필 조회 API", description = "유저 ID를 받아와서 프로필을 조회하는 API")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "회원 프로필 조회가 정상적으로 리턴된 경우")
            @ApiResponse(responseCode = "404", description = "회원 ID로 유저를 찾지 못한 경우")
    })
    @GetMapping("/{userId}/profile")
    public UserProfileQueryDto getUserProfile(@PathVariable Long id) {
        return userService.findUserProfileById(id);
    }
}