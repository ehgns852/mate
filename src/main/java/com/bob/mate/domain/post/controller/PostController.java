package com.bob.mate.domain.post.controller;

import com.bob.mate.domain.post.dto.*;
import com.bob.mate.domain.post.service.CommentService;
import com.bob.mate.domain.post.service.PostService;
import com.bob.mate.global.dto.CustomResponse;
import com.bob.mate.domain.post.dto.LikeResponse;
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
    private final CommentService commentService;

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

    @GetMapping("/{postId}/comments")
    public Page<CommentResponse> getAllComments(@PathVariable Long postId, Pageable pageable) {
        return commentService.getAllComments(postId, pageable);
    }

    @PostMapping("/{postId}/comments")
    public CustomResponse createComment(
            @PathVariable Long postId,
            @Valid @RequestBody CommentRequest commentRequest,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            throw new CustomException(ErrorCode.BAD_REQUEST_COMMENT);
        }

        return commentService.createComment(postId, commentRequest);
    }

    @PatchMapping("/{postId}/comments/{commentId}")
    public CustomResponse updateComment(
            @PathVariable Long postId, @PathVariable Long commentId,
            @Valid @RequestBody CommentRequest commentRequest
    ) {
            return commentService.updateComment(postId, commentId, commentRequest);
    }

    @DeleteMapping("/{postId}/comments/{commentId}")
    public CustomResponse deleteComment(
        @PathVariable Long postId, @PathVariable Long commentId
    ) {
        return commentService.deleteComment(postId, commentId);
    }

    @PatchMapping("/{postId}/comments/{commentId}/likes")
    public LikeResponse likeComment(
            @PathVariable Long postId, @PathVariable Long commentId
    ) {
        return commentService.likeComment(postId, commentId);
    }
}
