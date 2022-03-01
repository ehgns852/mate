package com.bob.mate.domain.user.controller;


import com.bob.mate.domain.user.dto.UserProfileRequest;
import com.bob.mate.domain.user.dto.UserRequest;
import com.bob.mate.domain.user.dto.UserResponse;
import com.bob.mate.domain.user.entity.User;
import com.bob.mate.domain.user.service.UserService;
import com.bob.mate.global.dto.CustomResponse;
import com.bob.mate.global.exception.CustomException;
import com.bob.mate.global.exception.ErrorCode;
import com.bob.mate.global.util.file.FileStore;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.net.MalformedURLException;


@RequestMapping("/user")
@RequiredArgsConstructor
@Slf4j
@RestController
public class UserController {
    private final UserService userService;
    private final FileStore fileStore;

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


    @Operation(summary = "유저 이름 수정 API", description = "Request Body 값을 받아와서 회원 이름을 수정하는 API")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "회원 이름이 정상적으로 수정된 경우"),
            @ApiResponse(responseCode = "400", description = "Request Body 값이 잘못된 경우"),
    })
    @PatchMapping("/{userId}/nickname")
    public CustomResponse updateNickname(@PathVariable Long userId,
                                         @Valid @RequestBody UserRequest userRequest,
                                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new CustomException(ErrorCode.BAD_REQUEST_NICKNAME);
        }
        return userService.createNickName(userId, userRequest);
    }


    @Operation(summary = "유저 정보 삭제 API", description = "유저 ID를 받아와서 삭제하는 API")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "회원 탈퇴가 정상적으로 성공된 경우"),
            @ApiResponse(responseCode = "404", description = "입력받은 회원 ID를 찾지 못한경우")
    })
    @DeleteMapping("/delete/{id}")
    public CustomResponse deleteUser(@PathVariable Long id) {
        return userService.deleteUser(id);
    }


    @PostMapping(value = "/{userId}/profile", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public UserResponse updateProfile(@PathVariable Long userId,
                                      @RequestPart(value = "file", required = false) MultipartFile multipartFile,
                                      @Validated @RequestPart(value = "json") UserProfileRequest userProfile,
                                      BindingResult bindingResult) throws IOException {
        if (bindingResult.hasErrors()) {
            throw new CustomException(ErrorCode.BAD_REQUEST_PROFILE);
        }
        return userService.updateProfile(userId, multipartFile, userProfile);
    }

    @GetMapping("/image/{filename}")
    public Resource showImage(@PathVariable String filename) throws MalformedURLException {
        log.info("in Resource image");
        if (!filename.equals("null")) {
            return new UrlResource("file:" + fileStore.getFullPath(filename));
        } else {
            return new UrlResource("https://dummyimage.com/450x300/dee2e6/6c757d.jpg");
        }
    }
}