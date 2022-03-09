package com.bob.mate.domain.post.repository;

import com.bob.mate.domain.post.dto.AllPostResponse;
import com.bob.mate.domain.post.dto.OnePostResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface PostCustomRepository {
    Page<AllPostResponse> findAllPosts(Pageable pageable);
    Optional<OnePostResponse> findPost(Long postId);
}
