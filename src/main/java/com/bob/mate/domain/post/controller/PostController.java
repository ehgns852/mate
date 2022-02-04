package com.bob.mate.domain.post.controller;

import com.bob.mate.domain.post.dto.PostRequest;
import com.bob.mate.domain.post.dto.PostResponse;
import com.bob.mate.domain.post.service.PostService;
import com.bob.mate.global.dto.CustomResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    @GetMapping
    public Page<PostResponse> getAllPosts() {
        return null;
    }

    @PostMapping
    public CustomResponse createPost(@Valid @RequestBody PostRequest postRequest, BindingResult bindingResult) {
        return null;
    }
}
