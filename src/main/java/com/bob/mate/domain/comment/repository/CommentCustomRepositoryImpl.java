package com.bob.mate.domain.comment.repository;

import com.bob.mate.domain.comment.dto.CommentResponse;
import com.bob.mate.domain.comment.entity.Comment;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static com.bob.mate.domain.comment.entity.QComment.comment;

@Repository
public class CommentCustomRepositoryImpl implements CommentCustomRepository{

    private final JPAQueryFactory jpaQueryFactory;

    public CommentCustomRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public Page<CommentResponse> findAllComments(Pageable pageable) {
        List<Comment> comments = jpaQueryFactory.selectFrom(comment).fetch();

        List<CommentResponse> res = new ArrayList<>();

        for (Comment comment : comments) {
            CommentResponse response = CommentResponse.builder()
                    .content(comment.getContent())
                    .profileUrl("test") // TODO : 조회되는지 확인
                    .username(comment.getUser().getUserProfile().getNickName())
                    .address(comment.getUser().getUserProfile().getAddress())
                    .createdAt(comment.getTimeEntity().getCreatedAt())
                    .build();

            res.add(response);
        }

        return new PageImpl<>(res, pageable, res.size());
    }
}
