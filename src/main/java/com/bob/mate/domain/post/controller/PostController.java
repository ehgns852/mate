package com.bob.mate.domain.post.controller;

import com.bob.mate.domain.post.dto.*;
import com.bob.mate.domain.post.service.CommentService;
import com.bob.mate.domain.post.service.PostService;
import com.bob.mate.global.dto.CustomResponse;
import com.bob.mate.domain.post.dto.LikeResponse;
import com.bob.mate.global.exception.CustomException;
import com.bob.mate.global.exception.ErrorCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @Operation(summary = "전체 글 조회 API", description = "모든 글 정보를 페이지 형태로 반환하는 API")
    @ApiResponse(responseCode = "200", description = "글 정보가 페이지 형태로 정상 리턴된 경우")
    @GetMapping
    public Page<AllPostResponse> getAllPosts(Pageable pageable) {
        return postService.getAllPosts(pageable);
    }

    @Operation(summary = "글 하나 조회 API", description = "글 ID 값을 받아서 해당 글의 정보를 반환하는 API")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "해당 글의 정보가 정상적으로 리턴된 경우"),
            @ApiResponse(responseCode = "404", description = "입력받은 postId 로 찾을 수 없는 경우")
    })
    @GetMapping("/{postId}")
    public OnePostResponse getPost(@PathVariable Long postId) {
        return postService.getPost(postId);
    }

    @Operation(summary = "글 생성 API", description = "Request Body 값을 받아와서 글을 생성하는 API")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "해당 글이 생성 되었을 경우"),
            @ApiResponse(responseCode = "400", description = "Request Body 값이 잘못된 경우"),
            @ApiResponse(responseCode = "404", description = "회원 가입된 유저가 아니어서 그 유저를 찾지 못하는 경우")
    })
    @PostMapping
    public CustomResponse createPost(@Valid @RequestBody PostRequest postRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new CustomException(ErrorCode.BAD_REQUEST_POST);
        }

        return postService.createPost(postRequest);
    }

    @Operation(summary = "글 수정 API", description = "Request Body 값을 받아와서 글을 수정하는 API")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "해당 글이 수정 되었을 경우"),
            @ApiResponse(responseCode = "400", description = "Request Body 값이 잘못된 경우"),
            @ApiResponse(responseCode = "403", description = "본인이 작성한 글이 아닌 다른 글을 수정하는 경우")
    })
    @PatchMapping("/{postId}")
    public CustomResponse updatePost(
            @Valid @RequestBody PostRequest postRequest,
            @PathVariable Long postId,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            throw new CustomException(ErrorCode.BAD_REQUEST_POST);
        }

        return postService.updatePost(postId, postRequest);
    }

    @Operation(summary = "글 삭제 API", description = "postId 값을 받아서 해당 글을 삭제하는 API")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "해당 글이 삭제 되었을 경우"),
            @ApiResponse(responseCode = "403", description = "본인이 작성한 글이 아닌 다른 글을 삭제하는 경우")
    })
    @DeleteMapping("/{postId}")
    public CustomResponse deletePost(@PathVariable Long postId) {
        return postService.deletePost(postId);
    }

    @Operation(summary = "글 좋아요 API", description = "postId 값을 받아서 해당 글의 좋아요 기능을 처리하는 API")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "해당 글에 좋아요 기능이 성공적으로 들어간 경우"),
            @ApiResponse(responseCode = "404", description = "회원 가입된 유저가 아니어서 그 유저를 찾지 못하는 경우")
    })
    @PatchMapping("/{postId}/likes")
    public LikeResponse likePost(@PathVariable Long postId, @RequestBody LikeRequest likeRequest) {
        return postService.likePost(postId, likeRequest);
    }
}
