package com.bob.mate.domain.post.repository;

import com.bob.mate.domain.post.dto.AllPostResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostCustomRepository {
    Page<AllPostResponse> findAllPosts(Pageable pageable);
}
