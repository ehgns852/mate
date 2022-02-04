package com.bob.mate.domain.post.repository;

import com.bob.mate.domain.post.dto.PostQueryDto;

import java.util.List;

public interface PostRepositoryCustom {
    List<PostQueryDto> findAllPost();
}
