package com.bob.mate.domain.post.repository;

import com.bob.mate.domain.post.entity.CommentLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {
}
