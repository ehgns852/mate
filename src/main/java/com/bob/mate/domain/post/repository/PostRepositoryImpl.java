package com.bob.mate.domain.post.repository;

import com.bob.mate.domain.post.domain.QPost;
import com.bob.mate.domain.post.domain.QPostTag;
import com.bob.mate.domain.post.dto.PostQueryDto;
import com.bob.mate.domain.post.dto.QPostQueryDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.bob.mate.domain.post.domain.QPost.*;
import static com.bob.mate.domain.post.domain.QPostTag.*;

@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepositoryCustom{

    private final JPAQueryFactory queryFactory;


    /**
     * left join post
     */
    @Override
    public List<PostQueryDto> findAllPost() {
        return queryFactory
                .select(new QPostQueryDto(
                        post.title,
                        post.content,
                        postTag.tag.name))
                .from(post)
                .leftJoin(post.postTags, postTag)
                .fetch();

    }
}
