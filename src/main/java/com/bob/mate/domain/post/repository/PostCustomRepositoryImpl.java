package com.bob.mate.domain.post.repository;

import com.bob.mate.domain.post.dto.AllPostResponse;
import com.bob.mate.domain.post.entity.Post;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static com.bob.mate.domain.post.entity.QPost.post;

@Repository
public class PostCustomRepositoryImpl implements PostCustomRepository{

    private final JPAQueryFactory jpaQueryFactory;

    public PostCustomRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public Page<AllPostResponse> findAllPosts(Pageable pageable) {
        List<Post> posts = jpaQueryFactory.selectFrom(post).fetch();

        List<AllPostResponse> res = new ArrayList<>();

        for (Post post : posts) {
            AllPostResponse response = AllPostResponse.builder()
                    .title(post.getTitle())
                    .content(post.getContent())
                    .profileUrl("test") // TODO : 제공 여부 확인
                    .address(post.getUser().getUserProfile().getAddress())
                    .createdAt(post.getTimeEntity().getCreatedAt())
                    .commentCount(post.getComments().size())
                    .likeCount(post.getLikeCount())
                    .viewCount(post.getViewCount())
                    .build();

            res.add(response);
        }

        return new PageImpl<>(res, pageable, res.size());
    }
}
