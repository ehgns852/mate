package com.bob.mate.domain.post.repository;

import com.bob.mate.domain.post.dto.CommentResponse;
import com.bob.mate.domain.post.entity.Comment;
import com.bob.mate.domain.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommentCustomRepository {
    Page<CommentResponse> findAllComments(Long postId, Pageable pageable, User user);
    Comment checkCommentOwner(Long postId, Long commentId);
}
