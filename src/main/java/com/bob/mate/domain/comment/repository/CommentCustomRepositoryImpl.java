package com.bob.mate.domain.comment.repository;

import com.bob.mate.domain.comment.dto.CommentResponse;
import com.bob.mate.domain.comment.dto.QCommentResponse;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;

import static com.bob.mate.domain.comment.entity.QComment.comment;

@RequiredArgsConstructor
public class CommentCustomRepositoryImpl implements CommentCustomRepository{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<CommentResponse> findAllComments(Pageable pageable) {
        List<CommentResponse> comments = jpaQueryFactory
                .select(new QCommentResponse(
                        comment.content, comment.user.userProfile.imageUrl,
                        comment.user.userProfile.nickName, comment.user.userProfile.address,
                        comment.timeEntity.createdDate
                ))
                .from(comment)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(comment.id.desc())
                .fetch();

        return PageableExecutionUtils.getPage(comments, pageable, () -> (long) comments.size());
    }
}
