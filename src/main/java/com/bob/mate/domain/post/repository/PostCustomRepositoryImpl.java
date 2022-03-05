package com.bob.mate.domain.post.repository;

import com.bob.mate.domain.post.dto.AllPostResponse;
import com.bob.mate.domain.post.dto.QAllPostResponse;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;

import static com.bob.mate.domain.post.entity.QComment.comment;
import static com.bob.mate.domain.post.entity.QPost.post;
import static com.bob.mate.domain.user.entity.QUser.user;
import static com.bob.mate.domain.user.entity.QUserProfile.userProfile;
import static com.bob.mate.global.util.file.QUploadFile.uploadFile;

@Slf4j
@RequiredArgsConstructor
public class PostCustomRepositoryImpl implements PostCustomRepository{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<AllPostResponse> findAllPosts(Pageable pageable) {
        List<AllPostResponse> posts = jpaQueryFactory
                .select(new QAllPostResponse(
                        post.title, uploadFile.storeFilename , userProfile.nickName,
                        post.timeEntity.createdDate, post.comments.size(), post.likeCount,
                        post.viewCount, userProfile.address
                ))
                .from(post)
                .innerJoin(post.user, user)
                .innerJoin(user.userProfile, userProfile)
                .innerJoin(userProfile.uploadFile, uploadFile)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(post.timeEntity.createdDate.desc())
                .fetch();

        return PageableExecutionUtils.getPage(posts, pageable, () -> (long) posts.size());
    }
}
