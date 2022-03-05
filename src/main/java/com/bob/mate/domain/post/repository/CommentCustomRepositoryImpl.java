package com.bob.mate.domain.post.repository;

import com.bob.mate.domain.post.dto.CommentResponse;
import com.bob.mate.domain.post.dto.QCommentResponse;
import com.bob.mate.domain.post.entity.Comment;
import com.bob.mate.domain.user.entity.User;
import com.bob.mate.global.util.Util;
import com.bob.mate.global.util.file.QUploadFile;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;

import static com.bob.mate.domain.user.entity.QUserProfile.userProfile;
import static com.bob.mate.domain.user.entity.QUser.user;
import static com.bob.mate.domain.post.entity.QComment.comment;
import static com.bob.mate.domain.post.entity.QPost.post;
import static com.bob.mate.global.util.file.QUploadFile.*;

@RequiredArgsConstructor
public class CommentCustomRepositoryImpl implements CommentCustomRepository{

    private final JPAQueryFactory jpaQueryFactory;
    private final Util util;

    @Override
    public Page<CommentResponse> findAllComments(Long postId, Pageable pageable) {
        List<CommentResponse> comments = jpaQueryFactory
                .select(new QCommentResponse(
                        comment.content, comment.likeCount, comment.liked,
                        uploadFile.storeFilename,
                        userProfile.nickName,
                        comment.timeEntity.createdDate
                ))
                .from(comment)
                .innerJoin(comment.user, user)
                .innerJoin(comment.post, post)
                .innerJoin(user.userProfile, userProfile)
                .innerJoin(userProfile.uploadFile, uploadFile)
                .where(post.id.eq(postId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(comment.id.desc())
                .fetch();

        return PageableExecutionUtils.getPage(comments, pageable, () -> (long) comments.size());
    }

    @Override
    public Comment checkCommentOwner(Long postId, Long commentId) {
        User currentUser = util.findCurrentUser();

        return jpaQueryFactory.selectFrom(comment)
                .innerJoin(comment.post, post)
                .innerJoin(comment.user, user)
                .where(
                        post.id.eq(postId).and(comment.id.eq(commentId))
                                .and(comment.user.id.eq(currentUser.getId()))
                )
                .fetchOne();
    }
}
