package com.bob.mate.domain.comment.repository;

import com.bob.mate.domain.comment.dto.CommentResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommentCustomRepository {
    Page<CommentResponse> findAllComments(Pageable pageable);
}
