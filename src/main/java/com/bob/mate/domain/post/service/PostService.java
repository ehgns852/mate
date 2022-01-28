package com.bob.mate.domain.post.service;

import com.bob.mate.domain.post.domain.Post;
import com.bob.mate.domain.post.dto.PostRequest;
import com.bob.mate.domain.post.dto.PostResponse;
import com.bob.mate.domain.post.mapper.PostMapper;
import com.bob.mate.domain.post.repository.PostRepository;
import com.bob.mate.global.dto.CustomResponse;
import com.bob.mate.global.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final PostMapper postMapper;

    @Transactional(readOnly = true)
    public ResponseEntity<List<PostResponse>> getAllPosts() {
        List<PostResponse> posts = postRepository.findAll().stream().map(postMapper::mapEntityToResponse).collect(Collectors.toList());

        return ResponseEntity.status(200).body(posts);
    }

    @Transactional
    public ResponseEntity<CustomResponse> createPost(PostRequest postRequest) {
        Post post = postMapper.mapRequestToEntity(postRequest);

        postRepository.save(post);

        CustomResponse response = new CustomResponse("글이 생성되었습니다.", 200);

        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @Transactional
    public ResponseEntity<CustomResponse> updatePost(Long postId, PostRequest postRequest) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new BadRequestException("해당 글을 찾을 수 없습니다."));

        postMapper.updateEntityFromDto(postRequest, post);

        CustomResponse response = new CustomResponse("글이 수정되었습니다.", 200);

        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @Transactional
    public ResponseEntity<CustomResponse> deletePost(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new BadRequestException("해당 글을 찾을 수 없습니다."));

        postRepository.delete(post);

        CustomResponse response = new CustomResponse("글이 삭제되었습니다.", 200);

        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
}
