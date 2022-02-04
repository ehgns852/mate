package com.bob.mate.domain.post.service;

import com.bob.mate.domain.post.dto.PostRequest;
import com.bob.mate.domain.post.repository.PostRepository;
import com.bob.mate.global.dto.CustomResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;

    public ResponseEntity getAllPosts(){
        return null;
    }

    @Transactional
    public ResponseEntity<CustomResponse> createPost(PostRequest postRequest) {
        return null;
    }
}
