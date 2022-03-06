package com.bob.mate.domain.post.dto;

import com.bob.mate.domain.user.entity.Address;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
@NoArgsConstructor
public class AllPostResponse {
    private String title;
    private String profileUrl;
    private String username;
    private OffsetDateTime createdAt;
    private Integer commentCount;
    private Integer likeCount;
    private Integer viewCount;
    private Address address;

    @QueryProjection
    public AllPostResponse(
            String title, String profileUrl, String username,
            OffsetDateTime createdAt, Integer commentCount, Integer likeCount,
            Integer viewCount, Address address
    ) {
        this.title = title;
        this.profileUrl = profileUrl;
        this.username = username;
        this.createdAt = createdAt;
        this.commentCount = commentCount;
        this.likeCount = likeCount;
        this.viewCount = viewCount;
        this.address = address;
    }
}
