package com.bob.mate.domain.post.repository;

import com.bob.mate.domain.post.dto.CommentResponse;
import com.bob.mate.domain.post.dto.QCommentResponse;
import com.bob.mate.domain.post.entity.Comment;
import com.bob.mate.domain.post.entity.CommentLike;
import com.bob.mate.domain.user.entity.User;
import com.bob.mate.global.util.Util;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;
import java.util.Optional;

import static com.bob.mate.domain.post.entity.QComment.comment;
import static com.bob.mate.domain.post.entity.QCommentLike.commentLike;
import static com.bob.mate.domain.post.entity.QPost.post;
import static com.bob.mate.domain.user.entity.QUploadFile.uploadFile;
import static com.bob.mate.domain.user.entity.QUser.user;
import static com.bob.mate.domain.user.entity.QUserProfile.userProfile;

@RequiredArgsConstructor
public class CommentCustomRepositoryImpl implements CommentCustomRepository{

    private final JPAQueryFactory jpaQueryFactory;
    private final Util util;

    @Override
    public Page<CommentResponse> findAllComments(Long postId, Pageable pageable, User member) {
        List<CommentResponse> comments = jpaQueryFactory
                .select(new QCommentResponse(
                        comment.id, comment.content, comment.likeCount,
                        uploadFile.storeFilename, userProfile.nickName,
                        comment.timeEntity.createdDate, userProfile.address
                ))
                .from(comment)
                .innerJoin(comment.user, user)
                .innerJoin(comment.post, post)
                .innerJoin(user.userProfile, userProfile)
                .innerJoin(userProfile.uploadFile, uploadFile)
                .where(post.id.eq(postId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(comment.timeEntity.createdDate.desc())
                .fetch();

        comments.forEach(c -> {
            Optional<CommentLike> optionalCommentLike = Optional.ofNullable(jpaQueryFactory
                    .selectFrom(commentLike)
                    .innerJoin(commentLike.comment, comment).fetchJoin()
                    .innerJoin(commentLike.user, user).fetchJoin()
                    .where(comment.id.eq(c.getCommentId()).and(user.id.eq(member.getId())))
                    .fetchOne());

            if (optionalCommentLike.isPresent()) c.setLiked(Boolean.TRUE);
            else c.setLiked(Boolean.FALSE);
        });

        return PageableExecutionUtils.getPage(comments, pageable, () -> (long) comments.size());
    }

    @Override
    public Comment checkCommentOwner(Long postId, Long commentId) {
        User currentUser = util.findCurrentUser();

        return jpaQueryFactory.selectFrom(comment)
                .innerJoin(comment.post, post).fetchJoin()
                .innerJoin(comment.user, user).fetchJoin()
                .where(
                        post.id.eq(postId).and(comment.id.eq(commentId))
                                .and(comment.user.id.eq(currentUser.getId()))
                )
                .fetchOne();
    }
}
