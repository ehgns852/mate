package com.bob.mate.domain.post.repository;

import com.bob.mate.domain.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long>, PostRepositoryCustom{
}
