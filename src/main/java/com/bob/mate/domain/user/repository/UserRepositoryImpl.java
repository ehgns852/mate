package com.bob.mate.domain.user.repository;

import com.bob.mate.domain.post.dto.MyCommentResponse;
import com.bob.mate.domain.post.dto.MyPostResponse;
import com.bob.mate.domain.post.dto.QMyCommentResponse;
import com.bob.mate.domain.post.dto.QMyPostResponse;
import com.bob.mate.domain.user.dto.QUserProfileQueryDto;
import com.bob.mate.domain.user.dto.UserProfileQueryDto;
import com.bob.mate.domain.user.entity.User;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;
import java.util.Optional;

import static com.bob.mate.domain.user.entity.QUploadFile.uploadFile;
import static com.bob.mate.domain.user.entity.QUser.user;
import static com.bob.mate.domain.user.entity.QUserProfile.userProfile;

import static com.bob.mate.domain.post.entity.QPost.post;
import static com.bob.mate.domain.post.entity.QComment.comment;

@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepositoryCustom{

    private final JPAQueryFactory queryFactory;



    @Override
    public User findByOauthId(String providerId) {
      return queryFactory
              .selectFrom(user)
              .innerJoin(user.userProfile, userProfile)
              .fetchJoin()
              .innerJoin(userProfile.uploadFile, uploadFile)
              .fetchJoin()
              .where(userProfile.providerId.eq(providerId))
              .fetchOne();
    }

    @Override
    public Optional<User> findUserAllProfileById(Long id) {
        return Optional.ofNullable(queryFactory
                .selectFrom(user)
                .innerJoin(user.userProfile, userProfile)
                .fetchJoin()
                .innerJoin(userProfile.uploadFile, uploadFile)
                .fetchJoin()
                .where(user.id.eq(id))
                .fetchOne());
    }

    @Override
    public Optional<UserProfileQueryDto> findUserProfileById(Long id) {
        return Optional.ofNullable(queryFactory
                .select(new QUserProfileQueryDto(
                        uploadFile.storeFilename,
                        userProfile.nickName,
                        userProfile.gender,
                        userProfile.address,
                        user.email,
                        userProfile.phoneNumber))
                .from(user)
                .innerJoin(user.userProfile, userProfile)
                .innerJoin(userProfile.uploadFile, uploadFile)
                .where(user.id.eq(id))
                .fetchOne());
    }

    @Override
    public Page<MyPostResponse> findAllMyPosts(Pageable pageable, User member) {
        List<MyPostResponse> res = queryFactory
                .select(new QMyPostResponse(
                        post.id, post.title, post.content,
                        post.likeCount, post.viewCount,
                        post.timeEntity.createdDate,
                        post.timeEntity.updatedDate
                ))
                .from(post)
                .innerJoin(post.user, user)
                .where(user.id.eq(member.getId()))
                .fetch();

        return PageableExecutionUtils.getPage(res, pageable, () -> (long) res.size());
    }

    @Override
    public Page<MyCommentResponse> findAllMyComments(Pageable pageable, User member) {
        List<MyCommentResponse> res = queryFactory
                .select(new QMyCommentResponse(
                        comment.id, comment.content,
                        comment.likeCount, comment.timeEntity.createdDate,
                        comment.timeEntity.updatedDate, post.id
                ))
                .from(comment)
                .innerJoin(comment.user, user)
                .innerJoin(comment.post, post)
                .where(user.id.eq(member.getId()))
                .fetch();

        return PageableExecutionUtils.getPage(res, pageable, () -> (long) res.size());
    }
}
