package com.bob.mate.domain.post.repository;

import com.bob.mate.domain.post.dto.AllPostResponse;
import com.bob.mate.domain.post.dto.OnePostResponse;
import com.bob.mate.domain.post.dto.QAllPostResponse;
import com.bob.mate.domain.post.dto.QOnePostResponse;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;

import static com.bob.mate.domain.post.entity.QPost.post;
import static com.bob.mate.domain.user.entity.QUser.user;
import static com.bob.mate.domain.user.entity.QUserProfile.userProfile;

@Slf4j
@RequiredArgsConstructor
public class PostCustomRepositoryImpl implements PostCustomRepository{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<AllPostResponse> findAllPosts(Pageable pageable) {
        List<AllPostResponse> posts = jpaQueryFactory
                .select(new QAllPostResponse(
                        post.title, post.content, post.user.userProfile.imageUrl, post.user.userProfile.nickName,
                        post.timeEntity.createdDate, post.comments.size(), post.likeCount,
                        post.viewCount
                ))
                .from(post)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(post.id.desc())
                .fetch();

        long countPosts = jpaQueryFactory
                .selectFrom(post)
                .fetch().size();

        return PageableExecutionUtils.getPage(posts, pageable, () -> countPosts);
    }

    @Override
    public OnePostResponse findPost(Long postId) {
        return jpaQueryFactory
                .select(new QOnePostResponse(
                        post.title, post.content, userProfile.imageUrl,
                        userProfile.nickName, post.timeEntity.createdDate,
                        post.likeCount, post.viewCount
                ))
                .from(post)
                .innerJoin(user).on(post.user.id.eq(user.id))
                .innerJoin(userProfile).on(user.userProfile.id.eq(userProfile.id))
                .where(post.id.eq(postId))
                .fetchOne();
    }
}
