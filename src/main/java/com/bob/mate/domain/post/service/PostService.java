package com.bob.mate.domain.post.service;

import com.bob.mate.domain.post.domain.Post;
import com.bob.mate.domain.post.dto.PostQueryDto;
import com.bob.mate.domain.post.dto.PostRequest;
import com.bob.mate.domain.post.repository.PostRepository;
import com.bob.mate.global.dto.CustomResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;

    public ResponseEntity getAllPosts(){
        List<PostQueryDto> allPost = postRepository.findAllPost();

        return ResponseEntity.status(200).body(allPost);
    }

    @Transactional
    public ResponseEntity<CustomResponse> createPost(PostRequest postRequest) {
        Post post = Post.addPost(postRequest);

        postRepository.save(post);

        CustomResponse response = new CustomResponse("글이 생성되었습니다.", 200);

        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

//    @Transactional
//    public ResponseEntity<CustomResponse> createPost1(PostRequest postRequest) {
//
//    }

//
//    @Transactional
//    public ResponseEntity<CustomResponse> updatePost(Long postId, PostRequest postRequest) {
//        Post post = postRepository.findById(postId).orElseThrow(() -> new BadRequestException("해당 글을 찾을 수 없습니다."));
//
//        postMapper.updateEntityFromDto(postRequest, post);
//
//        CustomResponse response = new CustomResponse("글이 수정되었습니다.", 200);
//
//        return ResponseEntity.status(response.getStatusCode()).body(response);
//    }
//
//    @Transactional
//    public ResponseEntity<CustomResponse> deletePost(Long postId) {
//        Post post = postRepository.findById(postId).orElseThrow(() -> new BadRequestException("해당 글을 찾을 수 없습니다."));
//
//        postRepository.delete(post);
//
//        CustomResponse response = new CustomResponse("글이 삭제되었습니다.", 200);
//
//        return ResponseEntity.status(response.getStatusCode()).body(response);
//    }
}
