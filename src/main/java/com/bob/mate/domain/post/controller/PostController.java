package com.bob.mate.domain.post.controller;

import com.bob.mate.domain.post.dto.AllPostResponse;
import com.bob.mate.domain.post.dto.OnePostResponse;
import com.bob.mate.domain.post.dto.PostRequest;
import com.bob.mate.domain.post.service.PostService;
import com.bob.mate.global.dto.CustomResponse;
import com.bob.mate.global.dto.LikeResponse;
import com.bob.mate.global.exception.CustomException;
import com.bob.mate.global.exception.ErrorCode;
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

    @GetMapping
    public Page<AllPostResponse> getAllPosts(Pageable pageable) {
        return postService.getAllPosts(pageable);
    }

    @GetMapping("/{postId}")
    public OnePostResponse getPost(@PathVariable Long postId) {
        return postService.getPost(postId);
    }

    @PostMapping
    public CustomResponse createPost(@Valid @RequestBody PostRequest postRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new CustomException(ErrorCode.BAD_REQUEST_POST);
        }

        return postService.createPost(postRequest);
    }

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

    @DeleteMapping("/{postId}")
    public CustomResponse deletePost(@PathVariable Long postId) {
        return postService.deletePost(postId);
    }

    @PatchMapping("/{postId}/likes")
    public LikeResponse likePost(@PathVariable Long postId) {
        return postService.likePost(postId);
    }
}
