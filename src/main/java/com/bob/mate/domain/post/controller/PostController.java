package com.bob.mate.domain.post.controller;

import com.bob.mate.domain.post.dto.PostRequest;
import com.bob.mate.domain.post.dto.PostResponse;
import com.bob.mate.domain.post.service.PostService;
import com.bob.mate.global.dto.CustomResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping("/posts")
    public ResponseEntity<List<PostResponse>> getAllPosts() {
        return postService.getAllPosts();
    }

    @PostMapping("/posts")
    public ResponseEntity<CustomResponse> createPost(@Valid @RequestBody PostRequest postRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            CustomResponse response = new CustomResponse("입력값을 확인해주세요", 400);
            return ResponseEntity.status(response.getStatusCode()).body(response);
        }

        return postService.createPost(postRequest);
    }

    @PatchMapping("/posts/{postId}")
    public ResponseEntity<CustomResponse> updatePost(
            @PathVariable Long postId,
            @Valid @RequestBody PostRequest postRequest,
            BindingResult bindingResult)
    {
        if (bindingResult.hasErrors()) {
            CustomResponse response = new CustomResponse("입력값을 확인해주세요", 400);
            return ResponseEntity.status(response.getStatusCode()).body(response);
        }

        return postService.updatePost(postId, postRequest);
    }

    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<CustomResponse> deletePost(@PathVariable Long postId) {
        return postService.deletePost(postId);
    }

}
