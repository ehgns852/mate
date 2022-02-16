package com.bob.mate.domain.post.repository;

import com.bob.mate.domain.post.dto.AllPostResponse;
import com.bob.mate.domain.post.dto.OnePostResponse;
import com.bob.mate.domain.post.dto.QAllPostResponse;
import com.bob.mate.domain.post.dto.QOnePostResponse;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;

import static com.bob.mate.domain.post.entity.QPost.post;
import static com.querydsl.core.group.GroupBy.list;

@RequiredArgsConstructor
public class PostCustomRepositoryImpl implements PostCustomRepository{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<AllPostResponse> findAllPosts(Pageable pageable) {
        List<AllPostResponse> posts = jpaQueryFactory
                .select(new QAllPostResponse(
                        post.title, post.content, post.user.userProfile.imageUrl,
                        post.user.userProfile.nickName, post.user.userProfile.address,
                        post.timeEntity.createdDate, post.comments.size(), post.likeCount,
                        post.viewCount
                ))
                .from(post)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(post.id.desc())
                .fetch();

        return PageableExecutionUtils.getPage(posts, pageable, () -> (long) posts.size());
    }

    @Override
    public OnePostResponse findPost(Long postId) {
        return jpaQueryFactory
                .select(new QOnePostResponse(
                        post.title, post.content, post.user.userProfile.imageUrl,
                        post.user.userProfile.nickName, post.user.userProfile.address,
                        post.timeEntity.createdDate, list(post.comments.any().content),
                        post.likeCount, post.viewCount, post.comments.size()
                ))
                .from(post)
                .where(post.id.eq(postId))
                .fetchOne();
    }
}
